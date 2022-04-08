package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.Par;
import cl.ahianf.rankbot.entity.Match;
import cl.ahianf.rankbot.entity.SinNombreDos;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.entity.Vote;
import cl.ahianf.rankbot.service.SinNombreDosService;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/mm")
public class MatchmakerRestController {
    Random rand = new Random();

    @Autowired
    private SongService songService;
    private SinNombreDosService sinNombreDosService;

    @Autowired
    public MatchmakerRestController(SongService theSongService, SinNombreDosService theSinNombreDosService) {
        songService = theSongService;
        sinNombreDosService = theSinNombreDosService;
    }

    @GetMapping
    public Match prueba() {
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());
        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;
        Par matchIdToPar = duelGenerator(matchId);

        Song songA = songService.findById(matchIdToPar.getLeft());
        Song songB = songService.findById(matchIdToPar.getRight());
        return new Match(songA, songB, matchId);
        //return matchId;

    }

    @PostMapping
    public void dfisod(@RequestBody Vote theVote) {

        int matchId = theVote.getMatchId();
        int vote = theVote.getVote();

        switch(vote){
            case 1:
                sinNombreDosService.incrementarWinsX(matchId);
                break;
            case 2:
                sinNombreDosService.incrementarWinsY(matchId);
                break;
            case 3:
                sinNombreDosService.incrementarDraws(matchId);
                break;
            case 4:
                sinNombreDosService.incrementarSkipped(matchId);
                break;
            default:
                throw new RuntimeException("invalid vote");
        }




        //return songService.findByFirstName("H");
//return null;

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


}
