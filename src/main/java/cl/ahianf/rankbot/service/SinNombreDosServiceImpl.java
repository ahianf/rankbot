package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.dao.SinNombreRepository;
import cl.ahianf.rankbot.entity.SinNombreDos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SinNombreDosServiceImpl implements SinNombreDosService {

    @Autowired
    private SinNombreRepository repository;

    @Override
    public SinNombreDos findById(int theId) {

        return repository.findById(theId)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado: " + theId));
    }

    @Override
    public List<SinNombreDos> findAll() {
        return repository.findAll();
    }

    @Override
    public void incrementarWinsX(int matchId) {
        repository.incrementarWinsX(matchId);
    }

    @Override
    public void incrementarWinsY(int matchId) {
        repository.incrementarWinsY(matchId);
    }

    @Override
    public void incrementarDraws(int matchId) {
        repository.incrementarDraws(matchId);
    }

    @Override
    public void incrementarSkipped(int matchId) {
        repository.incrementarSkipped(matchId);
    }


}
