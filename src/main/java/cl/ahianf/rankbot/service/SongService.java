/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Song;
import java.util.List;

public interface SongService {

    List<Song> findAll();

    void save(Song theSong);

    void deleteById(int theId);

    Song findById(int id);

    long count();

    void saveAll(List<Song> list);

    List<Song> findAllByOrderBySongIdAsc();

    List<Song> findAllByOrderByEloDesc();
}
