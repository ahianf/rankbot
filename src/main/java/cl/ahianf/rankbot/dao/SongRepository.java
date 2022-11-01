/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.dao;

import cl.ahianf.rankbot.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {

    List<Song> findAllByOrderBySongIdAsc();

    List<Song> findAllByOrderByEloDesc();

    Song findSongBySongIdAndArtist(int songId, int artist);

    long countAllByArtist(int artist);
}
