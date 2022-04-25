package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.Par;
import cl.ahianf.rankbot.entity.*;
import cl.ahianf.rankbot.service.ResultsService;
import cl.ahianf.rankbot.service.SongService;
import cl.ahianf.rankbot.service.VoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/mm")
public class MatchmakerRestController {
    Random rand = new Random();

    @Autowired
    private SongService songService;
    private ResultsService resultsService;
    private VoteLogService voteLogService;

    @Autowired
    public MatchmakerRestController(SongService theSongService, ResultsService theResultsService, VoteLogService theVoteLogService) {
        songService = theSongService;
        resultsService = theResultsService;
        voteLogService = theVoteLogService;
    }

    @GetMapping
    public Match prueba() {
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());
        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = unrollMatchId(matchId);

        Song songA = songService.findById(matchIdToPar.getLeft());
        Song songB = songService.findById(matchIdToPar.getRight());

        return new Match(songA, songB, matchId);

    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost")
    public Vote dfisod(@RequestBody Vote theVote, HttpServletRequest request) {

        int matchId = theVote.getMatchId();
        int vote = theVote.getVote();
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());

        inicializarDbResults();

        if (matchId > possibleMatchesUpperbound || matchId < 0) {
            vote = 0;
        }

        switch (vote) {
            case 1:
                resultsService.incrementarWinsX(matchId);
                break;
            case 2:
                resultsService.incrementarWinsY(matchId);
                break;
            case 3:
                resultsService.incrementarDraws(matchId);
                break;
            case 4:
                resultsService.incrementarSkipped(matchId);
                break;
            default:
                vote = 0;
                System.out.println("invalid vote");

        }

        voteLogService.save(new VoteLog(matchId, vote, request.getRemoteAddr(), Instant.now()));

        return theVote;
    }

    public static int nLessOneTriangular(int i) {
        i--;
        return ((i * i) + i) >> 1; //xd!
    }

    public static Par unrollMatchId(int matchId) {

        int inverseTriangular = (int) ((-1 + sqrt(1 + (8 * matchId))) / 2) + 1;
        int x;
        int y;

        if (nLessOneTriangular(inverseTriangular) == matchId) {
            x = inverseTriangular;
            y = x - 1;
        } else {
            x = inverseTriangular + 1;
            y = (nLessOneTriangular(x - 1) - matchId) * -1;
        }

        return new Par(x, y);
    }

    public void inicializarDbResults() {
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());
        int rowsMissing = (possibleMatchesUpperbound - (int) resultsService.count());

        List<Results> lista = new ArrayList<>();

        for (int i = 0; i < rowsMissing; i++) {
            lista.add(new Results(0, 0, 0, 0));
        }
        resultsService.saveAll(lista);
    }


}
