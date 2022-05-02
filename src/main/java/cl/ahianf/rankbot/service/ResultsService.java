package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Results;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResultsService {

    List<Results> findAll();

    @Transactional
    void incrementarWinsX(int matchId);

    @Transactional
    void incrementarWinsY(int matchId);

    @Transactional
    void incrementarDraws(int matchId);

    @Transactional
    void incrementarSkipped(int matchId);

    void saveAll(List<Results> lista);

    long count();
}
