/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service.impl;

import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.repository.ResultsRepository;
import cl.ahianf.rankbot.service.ResultsService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResultsServiceImpl implements ResultsService {

    private final ResultsRepository repository;

    public ResultsServiceImpl(ResultsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Results> findAll() {
        return repository.findAll();
    }

    @Override
    public void incrementarWinsX(int matchId, int artistId) {
        repository.incrementarWinsX(matchId, artistId);
    }

    @Override
    public void incrementarWinsY(int matchId, int artistId) {
        repository.incrementarWinsY(matchId, artistId);
    }

    @Override
    public void incrementarDraws(int matchId, int artistId) {
        repository.incrementarDraws(matchId, artistId);
    }

    @Override
    public void incrementarSkipped(int matchId, int artistId) {
        repository.incrementarSkipped(matchId, artistId);
    }

    @Override
    public void saveAll(List<Results> lista) {
        repository.saveAll(lista);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public int obtenerMax(int artistId) {

        Integer integer = repository.obtenerMax(artistId);
        if (integer == null) {
            integer = 0;
        }
        return integer;
    }
}
