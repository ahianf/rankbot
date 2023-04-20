/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Results;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ResultsService {

    List<Results> findAll();

    @Transactional
    void incrementarWinsX(int matchId, int artistId);

    @Transactional
    void incrementarWinsY(int matchId, int artistId);

    @Transactional
    void incrementarDraws(int matchId, int artistId);

    @Transactional
    void incrementarSkipped(int matchId, int artistId);

    void saveAll(List<Results> lista);

    long count();

    int obtenerMax(int artistId);
}
