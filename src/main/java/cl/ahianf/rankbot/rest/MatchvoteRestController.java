/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

import static cl.ahianf.rankbot.extra.Functions.*;

import cl.ahianf.rankbot.entity.*;
import cl.ahianf.rankbot.repository.ArtistRepository;
import cl.ahianf.rankbot.repository.ResultsRepository;
import cl.ahianf.rankbot.repository.SongRepository;
import cl.ahianf.rankbot.service.ResultsService;
import cl.ahianf.rankbot.service.SongService;
import cl.ahianf.rankbot.service.VoteLogService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/deathgrips")
public class MatchvoteRestController {

    Logger logger = LoggerFactory.getLogger(MatchvoteRestController.class);
    final Random rand = new Random();
    final Map<Long, Par> map =
            ExpiringMap.builder().maxSize(5000).expiration(90, TimeUnit.SECONDS).build();

    private final SongService songService;
    private final ResultsService resultsService;
    private final VoteLogService voteLogService;

    private final SongRepository songRepository;

    private final ArtistRepository artistRepository;

    private final ResultsRepository resultsRepository;

    public MatchvoteRestController(
            ResultsService resultsService,
            VoteLogService voteLogService,
            SongService songService,
            SongRepository songRepository,
            ArtistRepository artistRepository,
            ResultsRepository resultsRepository) {
        this.resultsService = resultsService;
        this.voteLogService = voteLogService;
        this.songService = songService;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.resultsRepository = resultsRepository;
    }

    @GetMapping
    public Match generarMatchRest(@RequestParam(defaultValue = "826255") Integer artist) {
        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound

        int possibleMatchesUpperbound =
                nMenosUnoTriangular((int) songRepository.countAllByArtistId(artist));

        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);

        Song songA = songRepository.findSongBySongIdAndArtistId(matchIdToPar.left(), artist);
        Song songB = songRepository.findSongBySongIdAndArtistId(matchIdToPar.right(), artist);

        long token = rand.nextLong(Long.MAX_VALUE);
        map.put(token, new Par(matchId, artist));

        Match match = new Match(songA, songB, matchId, token);
        logger.info("Nuevo match generado: " + match);
        return match;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<Vote> recibirVotoRest(
            @RequestBody Vote voteBody, HttpServletRequest request) {
        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound
        // inicializados a 0

        logger.info("Voto recibido: " + voteBody);

        long token = voteBody.getToken();

        if (map.get(token) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // token no existe
        }

        int vote = voteBody.getVote();
        int matchId = map.get(token).left();
        int artist = map.get(token).right();

        switch (vote) {
            case 1 -> resultsService.incrementarWinsX(matchId, artist);
            case 2 -> resultsService.incrementarWinsY(matchId, artist);
            case 3 -> resultsService.incrementarDraws(matchId, artist);
            case 4 -> resultsService.incrementarSkipped(matchId, artist);
            default -> {
                vote = 0; // 0 == voto invalido
                map.remove(token);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        map.remove(token);
        voteLogService.save(
                new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now(), artist));
        return new ResponseEntity<>(voteBody, HttpStatus.OK);
    }

    public void inicializarDbResults() {

        List<Artist> all = artistRepository.findAll();
        List<Results> lista = new ArrayList<>();

        for (Artist artist : all) {
            int artistId = artist.getId();
            long entriesByArtist = songRepository.countAllByArtistId(artistId);
            int possibleMatchesUpperbound = nMenosUnoTriangular((int) entriesByArtist);

            int max = resultsService.obtenerMax(artistId);

            for (int i = max; i < possibleMatchesUpperbound; i++) {
                lista.add(new Results(i + 1, 0, 0, 0, 0, artistId));
            }
        }
        resultsRepository.saveAll(lista);
    }
}
