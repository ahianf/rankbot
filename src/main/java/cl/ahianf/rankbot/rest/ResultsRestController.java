/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.rest;

import static cl.ahianf.rankbot.entity.Elo.eloRating;
import static cl.ahianf.rankbot.extra.Functions.unrollMatchId;

import cl.ahianf.rankbot.entity.Elo;
import cl.ahianf.rankbot.entity.Par;
import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.service.ResultsService;
import cl.ahianf.rankbot.service.SongService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deathgrips/results")
public class ResultsRestController {

    Logger logger = LoggerFactory.getLogger(ResultsRestController.class);
    private final ResultsService resultsService;
    private final SongService songService;

    public ResultsRestController(ResultsService resultsService, SongService songService) {
        this.resultsService = resultsService;
        this.songService = songService;
    }

    @GetMapping
    public List<Song> devolverSongsElo() {
        return songService.findAllByOrderByEloDesc();
    }

    //    @Scheduled(cron = "0 0/10 * * * *") // cálculo de Elo se ejecuta cada 10 minutos y se
    // guarda en
    // db/results
    public void calculateElo() {
        logger.info("Elo calculado correctamente");
        List<Results> listaResultados =
                resultsService
                        .findAll(); // se hace en memoria para evitar golpear la db excesivamente
        List<Song> listaCanciones =
                songService
                        .findAllByOrderBySongIdAsc(); // La db las envía sin orden, y estamos usando
        // indices.

        for (Song cancion : listaCanciones) {
            // Inicializar valores de Elo a 1000
            cancion.setElo(1000.0);
        }

        for (Results resultado : listaResultados) {

            int matchId = resultado.getMatchId();
            Par numeros = unrollMatchId(matchId);

            int songAIndex = numeros.left() - 1;
            int songBIndex = numeros.right() - 1;

            double eloSongA = listaCanciones.get(songAIndex).getElo();
            double eloSongB = listaCanciones.get(songBIndex).getElo();

            Elo eloCalculado = new Elo(eloSongA, eloSongB);

            // Este bloque permite que los valores de Elo sean evaluados de manera aleatoria,
            // de lo contrario los resultados se sesgan terriblemente a favor de los primeros
            // elementos
            // de la lista de canciones. Es una limitación de Elo.
            ArrayList<Integer> listaDeResultsShuffled = new ArrayList<>();
            for (int i = 0; i < resultado.getWinsX(); i++) listaDeResultsShuffled.add(1);

            for (int i = 0; i < resultado.getWinsY(); i++) listaDeResultsShuffled.add(2);

            for (int i = 0; i < resultado.getEmpates(); i++) listaDeResultsShuffled.add(3);

            Collections.shuffle(listaDeResultsShuffled);

            for (int i : listaDeResultsShuffled) {
                eloCalculado = eloRating(eloCalculado, i);
            }
            listaCanciones.get(songAIndex).setElo(eloCalculado.playerA());
            listaCanciones.get(songBIndex).setElo(eloCalculado.playerB());
        }
        songService.saveAll(listaCanciones);
    }
}
