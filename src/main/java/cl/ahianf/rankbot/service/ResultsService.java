package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Results;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResultsService {

    public Results findById(int theId);

    public List<Results> findAll();

    @Transactional
    public void incrementarWinsX(int matchId);

    @Transactional
    public void incrementarWinsY(int matchId);

    @Transactional
    public void incrementarDraws(int matchId);

    @Transactional
    public void incrementarSkipped(int matchId);

    public void saveAll(List<Results> lista);

    public long count();
}
