/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cl.ahianf.rankbot.extra.Functions.*;

@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/deathgrips")
public class MatchvoteRestController {
    final Random rand = new Random();
    final Map<Long, Integer> map =
            ExpiringMap.builder().maxSize(5000).expiration(90, TimeUnit.SECONDS).build();

    private final SongService songService;
    private final ResultsService resultsService;
    private final VoteLogService voteLogService;

    public MatchvoteRestController(
            ResultsService resultsService, VoteLogService voteLogService, SongService songService) {
        this.resultsService = resultsService;
        this.voteLogService = voteLogService;
        this.songService = songService;
    }

    @GetMapping
    public Match generarMatchRest() {
        int possibleMatchesUpperbound = nMenosUnoTriangular((int) songService.count());
        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);

        Song songA = songService.findById(matchIdToPar.left());
        Song songB = songService.findById(matchIdToPar.right());


        long token = rand.nextLong(Long.MAX_VALUE);
        map.put(token, matchId);

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
        int matchId = map.get(token);

//        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound
        // inicializados a 0

        switch (vote) {
            case 1 -> resultsService.incrementarWinsX(matchId);
            case 2 -> resultsService.incrementarWinsY(matchId);
            case 3 -> resultsService.incrementarDraws(matchId);
            case 4 -> resultsService.incrementarSkipped(matchId);
            default -> {
                vote = 0; // 0 == voto invalido
                map.remove(token);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        map.remove(token);
        voteLogService.save(new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now(), 2225756));
        return new ResponseEntity<>(voteBody, HttpStatus.OK);
    }

    @GetMapping("/s")
    public Vote foo() {
        return new Vote(2, 3, 4);
    }

    public void inicializarDbResults() {
        int possibleMatchesUpperbound = nMenosUnoTriangular((int) songService.count());
        int rowsMissing = (possibleMatchesUpperbound - (int) resultsService.count());

        List<Results> lista = new ArrayList<>();

        for (int i = 0; i < rowsMissing; i++) {
            lista.add(new Results(0, 0, 0, 0));
        }
        resultsService.saveAll(lista);
    }
}
