/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.repository;

import cl.ahianf.rankbot.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {

    List<Song> findAllByOrderBySongIdAsc();

    List<Song> findAllByArtistIdOrderBySongIdAsc(int artistId);

    List<Song> findAllByOrderByEloDesc();

    List<Song> findAllByArtistIdAndEnabledTrueOrderByEloDesc(int artistId);

    Song findSongBySongIdAndArtistIdAndEnabledIsTrue(int songId, int artistId);

    long countAllByArtistId(int artistId);
}
