/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.repository;

import cl.ahianf.rankbot.entity.Results;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ResultsRepository extends JpaRepository<Results, Integer> {

    // usando queries sql nativas para evitar traer un objeto completo, aparte de ineficiente
    // propenso a race conditions
    // https://stackoverflow.com/questions/27284487/jpa-hibernate-how-to-properly-increment-a-counter-in-the-database

    @Modifying
    @Query(
            "UPDATE Results u SET u.winsX = u.winsX + 1 where u.id.matchId = :matchId and"
                    + " u.id.artistId = :artistId")
    void incrementarWinsX(int matchId, int artistId);

    @Modifying
    @Query(
            "UPDATE Results u SET u.winsY = u.winsY + 1 where u.id.matchId = :matchId and"
                    + " u.id.artistId = :artistId")
    void incrementarWinsY(int matchId, int artistId);

    @Modifying
    @Query(
            "UPDATE Results u SET u.empates = u.empates + 1 where u.id.matchId = :matchId and"
                    + " u.id.artistId = :artistId")
    void incrementarDraws(int matchId, int artistId);

    @Modifying
    @Query(
            "UPDATE Results u SET u.skipped = u.skipped + 1 where u.id.matchId = :matchId and"
                    + " u.id.artistId = :artistId")
    void incrementarSkipped(int matchId, int artistId);

    @Query("select max(u.id.matchId) from Results u WHERE u.id.artistId = :artistId")
    Integer obtenerMax(int artistId);

    @Query("from Results u WHERE u.id.artistId = :artistId")
    List<Results> findAllByArtistId(int artistId);
}
