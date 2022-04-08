package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.Match;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.service.MatchService;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchRestController {
    @Autowired
    private MatchService matchService;

    @Autowired
    public MatchRestController(MatchService theMatchService) {
        matchService = theMatchService;

    }

    @GetMapping("{theId}")
    public Match getMatch(@PathVariable int theId){
        Match theMatch = matchService.findById(theId);

        return theMatch;

    }


}
