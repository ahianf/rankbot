package cl.ahianf.rankbot.dao;

import cl.ahianf.rankbot.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {

    public List<Song> findAllByOrderBySongIdAsc();

}

