package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.dao.MatchRepository;
import cl.ahianf.rankbot.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository repository;

    @Override
    public Match findById(int theId) {

        return repository.findById(theId)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado: " + theId));
    }
}
