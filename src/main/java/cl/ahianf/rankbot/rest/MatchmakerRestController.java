package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.Match;
import cl.ahianf.rankbot.service.MatchService;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mm")
public class MatchmakerRestController {
    @Autowired
    private SongService songService;

    @Autowired
    public MatchmakerRestController(SongService theSongService) {
        songService = theSongService;

    }

    @GetMapping
    public String prueba(){
        return songService.findAll().size() + "";
    }







}
