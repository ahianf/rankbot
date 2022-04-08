package cl.ahianf.rankbot.entity;

import cl.ahianf.rankbot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;

public class Matchmaker {

    @Autowired
    private SongService songService;

    @Autowired
    public Matchmaker(SongService theSongService){
        songService = theSongService;

    }

    public Matchmaker( ){
            }

    public void generarMatch(){

        // generar numero aleatorio de match_id
        // upperbound = triangular (tamaño lista de canciones - 1)
        // generar aleatorio

        int randomUpperbound = triangular(songService.findAll().size() - 1) ;



    }


    public static int triangular(int i) {
        return ((i * i) + i) / 2;
    }
}
