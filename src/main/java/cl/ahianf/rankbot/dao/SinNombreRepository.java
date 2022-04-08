package cl.ahianf.rankbot.dao;


import cl.ahianf.rankbot.entity.SinNombreDos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SinNombreRepository extends JpaRepository<SinNombreDos, Integer> {

    // usando queries sql nativas para evitar traer un objeto completo, aparte de ineficiente
    // propenso a race conditions
    // https://stackoverflow.com/questions/27284487/jpa-hibernate-how-to-properly-increment-a-counter-in-the-database

    @Modifying
    @Query("UPDATE SinNombreDos u SET u.winsX = u.winsX + 1 where u.matchId = :matchId")
    void incrementarWinsX(int matchId);

    @Modifying
    @Query("UPDATE SinNombreDos u SET u.winsY = u.winsY + 1 where u.matchId = :matchId")
    void incrementarWinsY(int matchId);

    @Modifying
    @Query("UPDATE SinNombreDos u SET u.draws = u.draws + 1 where u.matchId = :matchId")
    void incrementarDraws(int matchId);

    @Modifying
    @Query("UPDATE SinNombreDos u SET u.skipped = u.skipped + 1 where u.matchId = :matchId")
    void incrementarSkipped(int matchId);
}
