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
        Par matchIdToPar = duelGenerator(matchId);

        Song songA = songService.findById(matchIdToPar.getLeft());
        Song songB = songService.findById(matchIdToPar.getRight());



        return new Match(songA, songB, possibleMatchesUpperbound);
     //   return new Match(songA, songB, matchId);

    }

    @PostMapping
    public Vote dfisod(@RequestBody Vote theVote, HttpServletRequest request) {

        int matchId = theVote.getMatchId();
        int vote = theVote.getVote();
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());

        inicializarDbResults();

        if (matchId > possibleMatchesUpperbound || matchId < 0){
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

    public static Par duelGenerator(int matchId) {
        int x = 1;
        int y = 1;

        for (int i = 1; i <= matchId; i++) {
            if (y < x) {
                y++;
            }
            if (y == x) {
                y = 1;
                x++;
            }
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
