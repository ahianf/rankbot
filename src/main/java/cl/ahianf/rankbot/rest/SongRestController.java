package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@RestController
@RequestMapping("/songs")
public class SongRestController {
    @Autowired
    private SongService songService;

    @Autowired
    public SongRestController(SongService theSongService) {
        songService = theSongService;

    }

    @GetMapping
    public List<Song> findAll() {
        return songService.findAll();
    }

    @PostMapping
    public Song updateSong(@RequestBody Song theSong) {
        songService.save(theSong);
        return theSong;
    }




}
