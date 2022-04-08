package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.SinNombreDos;
import cl.ahianf.rankbot.entity.Song;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SinNombreDosService {

    public SinNombreDos findById(int theId);

    public List<SinNombreDos> findAll();

    @Transactional
    public void incrementarWinsX(int matchId);

    @Transactional
    public void incrementarWinsY(int matchId);

    @Transactional
    public void incrementarDraws(int matchId);

    @Transactional
    public void incrementarSkipped(int matchId);
}
