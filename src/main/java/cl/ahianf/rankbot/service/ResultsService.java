/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Results;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
