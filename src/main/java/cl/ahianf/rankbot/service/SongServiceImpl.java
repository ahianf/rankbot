package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.dao.SongRepository;
import cl.ahianf.rankbot.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository repository;

    @Override
    public List<Song> findAll() {
        var songs = (List<Song>) repository.findAll();

        return songs;
    }

    @Override
    public void save(Song theSong) {
        repository.save(theSong);
    }

    @Override
    public void deleteById(int theId) {
        repository.deleteById(theId);
    }

    @Override
    public Song findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado: " + id));

    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void saveAll(List<Song> list) {
        repository.saveAll(list);
    }

    @Override
    public List<Song> findAllByOrderBySongIdAsc() {
        return repository.findAllByOrderBySongIdAsc();
    }

}
