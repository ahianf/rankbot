package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.config.annotation.RateLimited;
import cl.ahianf.rankbot.entity.*;
import cl.ahianf.rankbot.entity.dto.MessageDto;
import cl.ahianf.rankbot.extra.Functions;
import cl.ahianf.rankbot.service.JdbiService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static cl.ahianf.rankbot.entity.Elo.eloRating;
import static cl.ahianf.rankbot.extra.Functions.nMenosUnoTriangular;
import static cl.ahianf.rankbot.extra.Functions.unrollMatchId;

@RestController
@RequestMapping("/api/v2")
public class ResourceController {

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'OIDC_USER')")
    public ResponseEntity<MessageDto> user(Authentication authentication) {
        return ResponseEntity.ok(new MessageDto("Hello " + authentication.getName()));
    }

    JdbiService jdbiService;
    Logger logger = LoggerFactory.getLogger(ResourceController.class);
    final Random rand = new Random();
    Map<String, Integer> hashMap;
    final Map<Long, Triple> map =
            ExpiringMap.builder().maxSize(50000).expiration(60, TimeUnit.SECONDS).build();
    final Map<UUID, Integer> userEloCalculateTime =
            ExpiringMap.builder().maxSize(50000).expiration(1, TimeUnit.MINUTES).build();



    @Autowired
    public ResourceController(MeterRegistry registry, JdbiService jdbiService) {
        this.jdbiService = jdbiService;

        this.hashMap = new HashMap<>();

        List<Artist> all = jdbiService.findAllArtist();
        for (Artist i : all) {
            hashMap.put(i.getName().toLowerCase(), i.getId());
        }

        Gauge.builder("rankbotcontroller.expiringmap.size", fetchExpiringMapSize())
                .description("Current size of ExpiringMap")
                .register(registry);
    }

    @RateLimited(45)
    @GetMapping("/match/{artist}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'OIDC_USER')")
    public ResponseEntity<Match> generarMatchRest(
            @PathVariable(value = "artist") String param, HttpServletRequest request, Authentication authentication) {

        Integer artist = hashMap.get(param.toLowerCase().replace('-', ' '));

        if (artist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        int possibleMatchesUpperbound = nMenosUnoTriangular(jdbiService.countAllByArtistId(artist));

        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);
        Song songA = jdbiService.encontrarSong(matchIdToPar.left(), artist);
        Song songB = jdbiService.encontrarSong(matchIdToPar.right(), artist);

        long token = rand.nextLong(9007199254740991L); // JavaScript max value
        map.put(token, new Triple(matchId, artist, authentication.getName()));

        Match match = new Match(songA, songB, matchId, token);
        logger.info(
                "Match: " + songA.getArtist() + " | " + match + ", user: " + authentication.getName());

        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @RateLimited(23)
    @PostMapping("/match")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'OIDC_USER')")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<Vote> recibirVotoRest(
            @RequestBody Vote voteBody, HttpServletRequest request) {

        logger.info("Voto recibido: " + voteBody);

        long token = voteBody.getToken();

        if (map.get(token) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // token no existe
        }

        int vote = voteBody.getVote();
        int matchId = map.get(token).left();
        int artist = map.get(token).right();
        String user = map.get(token).center();
        UUID uuid = Functions.stringToUUID(user);

        switch (vote) {
            case 1 -> jdbiService.incrementarWinsXByUser(matchId, artist, uuid);
            case 2 -> jdbiService.incrementarWinsYByUser(matchId, artist, uuid);
            case 3 -> jdbiService.incrementarDrawsByUser(matchId, artist, uuid);
            case 4 -> jdbiService.incrementarSkippedByUser(matchId, artist, uuid);
            default -> {
                map.remove(token);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        map.remove(token);
        jdbiService.voteLogSave(
                new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now(), artist, user, uuid));
        return new ResponseEntity<>(voteBody, HttpStatus.OK);
    }

    @RateLimited(100)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'OIDC_USER')")
    @GetMapping("/results/{artist}")
    public List<Song> devolverSongsElo(@PathVariable(value = "artist") String artist, @RequestParam(name = "global", defaultValue = "false") boolean global, Authentication authentication) {
        int artistId = hashMap.get(artist.toLowerCase().replace('-', ' '));

        if (global) {
            return jdbiService.obtenerCanciones(artistId);
        } else {
            UUID uuid = Functions.stringToUUID(authentication.getName());

            if (!userEloCalculateTime.containsKey(uuid)) {
                calculateEloByUser(uuid, artistId);
                userEloCalculateTime.put(uuid, null); // dummy value
            }

            return jdbiService.obtenerCancionesByUser(artistId, uuid);
        }
    }

    private void calculateEloByUser(UUID uuid, int artistId) {
        long startTime = System.nanoTime();

        List<Results> listaResultados =
                jdbiService.findAllByArtistIdAndUserUuid(
                        artistId, uuid); // se hace en memoria para evitar golpear la db excesivamente

        List<Song> listaCanciones =
                jdbiService.findAllByArtistIdOrderBySongIdAsc(
                        artistId); // La db las envía sin orden, y estamos usando indices.
        for (Song cancion : listaCanciones) {
            // Inicializar valores de Elo a 1000
            cancion.setElo(1000.0);
        }

        for (Results resultado : listaResultados) {

            int matchId = resultado.getMatchId();

            int songAIndex = unrollMatchId(matchId).left() - 1;
            int songBIndex = unrollMatchId(matchId).right() - 1;

            double eloSongA = listaCanciones.get(songAIndex).getElo();
            double eloSongB = listaCanciones.get(songBIndex).getElo();

            Elo eloCalculado = new Elo(eloSongA, eloSongB);

            // Este bloque permite que los valores de Elo sean evaluados de manera aleatoria,
            // de lo contrario los resultados se sesgan terriblemente a favor de los primeros
            // elementos
            // de la lista de canciones. Es una limitación de Elo.
            ArrayList<Integer> listaDeResultsShuffled = new ArrayList<>();
            for (int i = 0; i < resultado.getWinsX(); i++) {
                listaDeResultsShuffled.add(1);
            }

            for (int i = 0; i < resultado.getWinsY(); i++) {
                listaDeResultsShuffled.add(2);
            }

            for (int i = 0; i < resultado.getDraws(); i++) {
                listaDeResultsShuffled.add(3);
            }

            Collections.shuffle(listaDeResultsShuffled);

            for (int i : listaDeResultsShuffled) {
                eloCalculado = eloRating(eloCalculado, i);
            }
            listaCanciones.get(songAIndex).setElo(eloCalculado.playerA());
            listaCanciones.get(songBIndex).setElo(eloCalculado.playerB());
        }

        List<Song> filteredList = listaCanciones.parallelStream().filter(i -> !i.getElo().equals(1000.0)).toList();

        jdbiService.saveAllListaSongsPerUser(filteredList, uuid);


        long endTime = System.nanoTime();
        long duration = (endTime - startTime); // divide by 1000000 to get milliseconds.
        logger.info("Elo scores calculated for user " + uuid + ". Time elapsed: " + duration / 1000000 + " ms");
    }

    private Supplier<Number> fetchExpiringMapSize() {
        return map::size;
    }

}
