package cl.ahianf.rankbot.dao;

import cl.ahianf.rankbot.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
