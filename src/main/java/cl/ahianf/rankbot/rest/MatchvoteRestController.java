/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

import static cl.ahianf.rankbot.extra.Functions.*;

import cl.ahianf.rankbot.dao.SongRepository;
import cl.ahianf.rankbot.entity.*;
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

    public MatchvoteRestController(
            ResultsService resultsService,
            VoteLogService voteLogService,
            SongService songService,
            SongRepository songRepository) {
        this.resultsService = resultsService;
        this.voteLogService = voteLogService;
        this.songService = songService;
        this.songRepository = songRepository;
    }

    @GetMapping
    public Match generarMatchRest(@RequestParam(defaultValue = "2225756") Integer artist) {
        int possibleMatchesUpperbound =
                nMenosUnoTriangular((int) songRepository.countAllByArtist(artist));
        logger.info("possibleMatchesUpperbound: " + possibleMatchesUpperbound);

        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        logger.info("MatchId: " + matchId);
        Par matchIdToPar = unrollMatchId(matchId);

        Song songA = songRepository.findSongBySongIdAndArtist(matchIdToPar.left(), artist);
        Song songB = songRepository.findSongBySongIdAndArtist(matchIdToPar.right(), artist);

        long token = rand.nextLong(Long.MAX_VALUE);
        map.put(token, new Par(matchId, artist));

        return new Match(songA, songB, matchId, token);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<Vote> recibirVotoRest(
            @RequestBody Vote voteBody, HttpServletRequest request) {

        long token = voteBody.getToken();

        if (map.get(token) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // token no existe
        }

        int vote = voteBody.getVote();
        int matchId = map.get(token).left();
        int artist = map.get(token).right();

        //        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound
        // inicializados a 0

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
        int possibleMatchesUpperbound = nMenosUnoTriangular((int) songService.count());
        int rowsMissing = (possibleMatchesUpperbound - (int) resultsService.count());

        List<Results> lista = new ArrayList<>();

        for (int i = 0; i < rowsMissing; i++) {
            lista.add(new Results(0, 0, 0, 0, 2225756));
        }
        resultsService.saveAll(lista);
    }
}
