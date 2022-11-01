/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service.impl;

import cl.ahianf.rankbot.dao.SongRepository;
import cl.ahianf.rankbot.entity.Song;
import java.util.List;
import javax.persistence.EntityNotFoundException;

import cl.ahianf.rankbot.service.SongService;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository repository;

    public SongServiceImpl(SongRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Song> findAll() {
        return repository.findAll();
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
        return repository
                .findById(id)
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

    @Override
    public List<Song> findAllByOrderByEloDesc() {
        return repository.findAllByOrderByEloDesc();
    }
}
