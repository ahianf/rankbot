/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.dao.ResultsRepository;
import cl.ahianf.rankbot.entity.Results;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultsServiceImpl implements ResultsService {

    @Autowired private ResultsRepository repository;

    @Override
    public List<Results> findAll() {
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

    @Override
    public void saveAll(List<Results> lista) {
        repository.saveAll(lista);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
