/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Artist;
import cl.ahianf.rankbot.entity.Song;
import java.util.List;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JdbiService {

    private final Jdbi jdbi;

    public JdbiService(@Qualifier("postgresJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Artist> findAllArtist() {
        String sql = "SELECT * FROM public.artist_credit";
        return jdbi.withHandle(handle -> handle.createQuery(sql).mapToBean(Artist.class).list());
    }

    public List<Song> obtenerCanciones(int artist) {

        var sql =
                """
                    SELECT x.* FROM public.track_app x
                    WHERE artist_id = :artist and enabled = true
                    order by elo desc
                """;

        return jdbi.withHandle(
                handle -> handle.createQuery(sql)
                        .bind("artist", artist)
                        .mapToBean(Song.class)
                        .list());
    }
}
