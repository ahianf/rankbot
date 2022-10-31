/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

import static java.lang.Math.sqrt;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/deathgrips")
public class MatchvoteRestController {
    final Random rand = new Random();
    final Map<Integer, Integer> map =
            ExpiringMap.builder().maxSize(5000).expiration(90, TimeUnit.SECONDS).build();

    private final SongService songService;
    private final ResultsService resultsService;
    private final VoteLogService voteLogService;

    @Autowired
    public MatchvoteRestController(
            ResultsService theResultsService,
            VoteLogService theVoteLogService,
            SongService songService) {
        this.resultsService = theResultsService;
        this.voteLogService = theVoteLogService;
        this.songService = songService;
    }

    @GetMapping
    public Match generarMatchRest() {
        int possibleMatchesUpperbound = nMenosUnoTriangular((int) songService.count());
        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);

        Song songA = songService.findById(matchIdToPar.left());
        Song songB = songService.findById(matchIdToPar.right());

        int token = rand.nextInt(2147483647);
        map.put(token, matchId);

        return new Match(songA, songB, matchId, token);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<Vote> recibirVotoRest(
            @RequestBody Vote theVote, HttpServletRequest request) {
        int token = theVote.getToken();

        if (map.get(token) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // token no existe
        }
        int vote = theVote.getVote();
        int matchId = map.get(token);
        inicializarDbResults(); // en caso que falten, genera valores hasta el upperbound
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
        voteLogService.save(new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now()));
        return new ResponseEntity<>(theVote, HttpStatus.OK);
    }

    public static int nMenosUnoTriangular(int i) {
        i--;
        return ((i * i) + i) / 2; // xd!
    }

    public static Par unrollMatchId(int matchId) {

        int inverseTriangular = (int) ((-1 + sqrt(1 + (8 * matchId))) / 2) + 1;
        int x;
        int y;

        if (nMenosUnoTriangular(inverseTriangular) == matchId) {
            x = inverseTriangular;
            y = x - 1;
        } else {
            x = inverseTriangular + 1;
            y = (nMenosUnoTriangular(x - 1) - matchId) * -1;
        }
        return new Par(x, y);
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
