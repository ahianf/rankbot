package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.Par;
import cl.ahianf.rankbot.ParElo;
import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.service.ResultsService;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.ahianf.rankbot.Elo.eloRating;
import static cl.ahianf.rankbot.entity.Test.matchIdToParAlgebraic;

@RestController
@RequestMapping("/results")
public class ResultsRestController {
    @Autowired
    private ResultsService resultsService;
    private SongService songService;

    @Autowired
    public ResultsRestController(ResultsService theResultsService, SongService theSongService) {
        resultsService = theResultsService;
        songService = theSongService;
    }

    @GetMapping("{theId}")
    public Results getMatch(@PathVariable int theId) {
        Results theResults = resultsService.findById(theId);
        return theResults;
    }

    @GetMapping("/uwu")
    public List<Song> calculateElo() {
        List<Results> listaResultados = resultsService.findAll();
        List<Song> listaSong = songService.findAllByOrderBySongIdAsc();

        for (Results i : listaResultados){

            int matchId = i.getMatchId();
            ParElo parelo = matchIdToParElo(matchId);

            ParElo uwu = resultadoAParElo(parelo, i);

            Par numeros = matchIdToParAlgebraic(matchId);

            int left = numeros.getLeft();
            int right = numeros.getRight();


            listaSong.get(left - 1).setElo(uwu.getLeft());
            listaSong.get(right - 1).setElo(uwu.getRight());
        }



        songService.saveAll(listaSong);

        return listaSong;

    }


    public ParElo matchIdToParElo(int matchId) {

        Par numeros = matchIdToParAlgebraic(matchId);

        int left = numeros.getLeft();
        int right = numeros.getRight();

        List<Song> listaSong = songService.findAllByOrderBySongIdAsc();
//        System.out.println(listaSong);

//        double eloSongA = listaSong.get(left - 1).getElo();
//        double eloSongB = listaSong.get(right - 1).getElo();

        double eloSongA = 1000;
        double eloSongB = 1000;
//
//        System.out.println("eloleft, " + eloSongA + " eloreght, " + eloSongB);
//        System.out.println("left, " + left + " reght, " + right);

        return new ParElo(eloSongA, eloSongB);
    }

    private static ParElo resultadoAParElo(ParElo parelo, Results resultado) {
        ArrayList<Integer> listOfDoubles = new ArrayList<>();
        for (int i = 0; i < resultado.getWinsX(); i++) {
            listOfDoubles.add(1);
        }

        for (int i = 0; i < resultado.getWinsY(); i++) {
            listOfDoubles.add(2);
        }

        for (int i = 0; i < resultado.getDraws(); i++) {
            listOfDoubles.add(3);
        }
        Collections.shuffle(listOfDoubles);

        for (int i : listOfDoubles) {
            parelo = eloRating(parelo, i);
        }
        return parelo;
    }

}