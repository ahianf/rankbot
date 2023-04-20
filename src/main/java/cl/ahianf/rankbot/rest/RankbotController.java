/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.*;
import cl.ahianf.rankbot.service.JdbiService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static cl.ahianf.rankbot.extra.Functions.nMenosUnoTriangular;
import static cl.ahianf.rankbot.extra.Functions.unrollMatchId;

@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/api")
public class RankbotController {

    JdbiService jdbiService;
    Logger logger = LoggerFactory.getLogger(RankbotController.class);
    final Random rand = new Random();
    Map<String, Integer> hashMap;
    final Map<Long, Par> map =
            ExpiringMap.builder().maxSize(50000).expiration(60, TimeUnit.SECONDS).build();

    @Autowired
    public RankbotController(MeterRegistry registry, JdbiService jdbiService) {
        this.jdbiService = jdbiService;

        this.hashMap = new HashMap<>();

        //        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound
        // inicializados a 0

        List<Artist> all = jdbiService.findAllArtist();
        for (Artist i : all) {
            hashMap.put(i.getName().toLowerCase(), i.getId());
        }

        Gauge.builder("rankbotcontroller.expiringmap.size", fetchExpiringMapSize())
                .description("Current size of ExpiringMap")
                .register(registry);
    }

    @GetMapping("/test/")
    public String generarMatchRest() {

        return "d";
    }

    @GetMapping("/match/{artist}")
    public ResponseEntity<Match> generarMatchRest(
            @PathVariable(value = "artist") String param, HttpServletRequest request) {

        Integer artist = hashMap.get(param.toLowerCase().replace('-', ' '));

        if (artist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        int possibleMatchesUpperbound =
                nMenosUnoTriangular((int) songRepository.countAllByArtistId(artist));

        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);
        Song songA =
                songRepository.findSongBySongIdAndArtistIdAndEnabledIsTrue(matchIdToPar.left(), artist);
        Song songB =
                songRepository.findSongBySongIdAndArtistIdAndEnabledIsTrue(matchIdToPar.right(), artist);

        long token = rand.nextLong(9007199254740991L); // JavaScript max value
        map.put(token, new Par(matchId, artist));

        Match match = new Match(songA, songB, matchId, token);
        logger.info(
                "Match: " + songA.getArtist() + "; " + match + ", ip: " +
                        request.getRemoteAddr());

        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    //
    //    @PostMapping("/match")
    //    @CrossOrigin(origins = "http://localhost")
    //    public ResponseEntity<Vote> recibirVotoRest(
    //            @RequestBody Vote voteBody, HttpServletRequest request) {
    //
    //        logger.info("Voto recibido: " + voteBody);
    //
    //        long token = voteBody.getToken();
    //
    //        if (map.get(token) == null) {
    //            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // token no existe
    //        }
    //
    //        int vote = voteBody.getVote();
    //        int matchId = map.get(token).left();
    //        int artist = map.get(token).right();
    //
    //        switch (vote) {
    //            case 1 -> resultsService.incrementarWinsX(matchId, artist);
    //            case 2 -> resultsService.incrementarWinsY(matchId, artist);
    //            case 3 -> resultsService.incrementarDraws(matchId, artist);
    //            case 4 -> resultsService.incrementarSkipped(matchId, artist);
    //            default -> {
    //                map.remove(token);
    //                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //            }
    //        }
    //
    //        map.remove(token);
    //        voteLogService.save(
    //                new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now(), artist));
    //
    //        return new ResponseEntity<>(voteBody, HttpStatus.OK);
    //    }
    //
    //    public void inicializarDbResults() {
    //
    //        List<Artist> all = artistRepository.findAll();
    //        List<Results> lista = new ArrayList<>();
    //
    //        for (Artist artist : all) {
    //            int artistId = artist.getId();
    //            long entriesByArtist = songRepository.countAllByArtistId(artistId);
    //            int possibleMatchesUpperbound = nMenosUnoTriangular((int) entriesByArtist);
    //
    //
    //            int max = resultsService.obtenerMax(artistId);
    //
    //            for (int i = max; i < possibleMatchesUpperbound; i++) {
    //                int matchId = i + 1;
    //                Par p = unrollMatchId(matchId);
    //                lista.add(new Results(matchId, 0, 0, 0, 0, artistId, p.left(), p.right()));
    //            }
    //        }
    //        resultsRepository.saveAll(lista);
    //    }
    //
    @GetMapping("/results/{artist}")
    public List<Song> devolverSongsElo(@PathVariable(value = "artist") String param) {
        int artist = hashMap.get(param.toLowerCase().replace('-', ' '));

        return jdbiService.obtenerCanciones(artist);

    }
    //
    //
    //
    ////    @Scheduled(fixedRateString = "${rankbot.elo.scheduling}")
    //    public void calculateElo() {
    //        long startTime = System.nanoTime();
    //        List<Artist> all = artistRepository.findAll();
    //
    //        for (Artist artist : all) {
    //            int artistId = artist.getId();
    //
    //            List<Results> listaResultados =
    //                    resultsRepository.findAllByArtistId(
    //                            artistId); // se hace en memoria para evitar golpear la db
    // excesivamente
    //
    //            List<Song> listaCanciones =
    //                    songRepository.findAllByArtistIdOrderBySongIdAsc(
    //                            artistId); // La db las envía sin orden, y estamos usandoindices.
    //
    //            for (Song cancion : listaCanciones) {
    //                // Inicializar valores de Elo a 1000
    //                cancion.setElo(1000.0);
    //            }
    //
    //            for (Results resultado : listaResultados) {
    //
    //                int matchId = resultado.getMatchId();
    //
    //                int songAIndex = unrollMatchId(matchId).left() - 1;
    //                int songBIndex = unrollMatchId(matchId).right() - 1;
    //
    //                double eloSongA = listaCanciones.get(songAIndex).getElo();
    //                double eloSongB = listaCanciones.get(songBIndex).getElo();
    //
    //                Elo eloCalculado = new Elo(eloSongA, eloSongB);
    //
    //                // Este bloque permite que los valores de Elo sean evaluados de manera
    // aleatoria,
    //                // de lo contrario los resultados se sesgan terriblemente a favor de los
    // primeros
    //                // elementos
    //                // de la lista de canciones. Es una limitación de Elo.
    //                ArrayList<Integer> listaDeResultsShuffled = new ArrayList<>();
    //                for (int i = 0; i < resultado.getWinsX(); i++) listaDeResultsShuffled.add(1);
    //
    //                for (int i = 0; i < resultado.getWinsY(); i++) listaDeResultsShuffled.add(2);
    //
    //                for (int i = 0; i < resultado.getEmpates(); i++)
    // listaDeResultsShuffled.add(3);
    //
    //                Collections.shuffle(listaDeResultsShuffled);
    //
    //                for (int i : listaDeResultsShuffled) {
    //                    eloCalculado = eloRating(eloCalculado, i);
    //                }
    //                listaCanciones.get(songAIndex).setElo(eloCalculado.playerA());
    //                listaCanciones.get(songBIndex).setElo(eloCalculado.playerB());
    //            }
    //            songRepository.saveAll(listaCanciones);
    //        }
    //
    //        long endTime = System.nanoTime();
    //        long duration = (endTime - startTime); // divide by 1000000 to get milliseconds.
    //        logger.info("Elo scores calculated. Time elapsed: " + duration / 1000000 + " ms");
    //    }

    private Supplier<Number> fetchExpiringMapSize() {
        return map::size;
    }
}
