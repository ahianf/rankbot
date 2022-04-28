package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Song;

import java.util.List;

public interface SongService {

    public List<Song> findAll();

    public void save(Song theSong);

    public void deleteById(int theId);

    public Song findById(int id);

    public long count();

    public void saveAll(List <Song> list);

    public List<Song> findAllByOrderBySongIdAsc();

    public List<Song> findAllByOrderByEloDesc();
}
