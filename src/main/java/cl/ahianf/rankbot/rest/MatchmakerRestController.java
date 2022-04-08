package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.SinNombreAun;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/mm")
public class MatchmakerRestController {
    Random rand = new Random();

    @Autowired
    private SongService songService;

    @Autowired
    public MatchmakerRestController(SongService theSongService) {
        songService = theSongService;

    }

    @GetMapping
    public int prueba() {
        int possibleMatchesUpperbound = nLessOneTriangular((int) songService.count());
        int matchId = rand.nextInt(possibleMatchesUpperbound) + 1;

        Song songA = songService.findById(1);
        Song songB = songService.findById(2);
        //return new SinNombreAun(songA, songB, 200);
        return matchId;

    }

    public static int nLessOneTriangular(int i){
        i--;
        return ((i * i) + i) >> 1; //xd!
    }


}
