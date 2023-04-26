/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Artist;
import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.entity.VoteLog;
import java.util.List;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JdbiService {

    private final Jdbi jdbi;
    Logger log = LoggerFactory.getLogger(JdbiService.class);

    public JdbiService(@Qualifier("postgresJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.setSqlLogger(new Slf4JSqlLogger(log));
    }

    public List<Artist> findAllArtist() {
        String sql = "SELECT * FROM public.artist_credit";
        return jdbi.withHandle(handle -> handle.createQuery(sql).mapToBean(Artist.class).list());
    }

    public List<Song> obtenerCanciones(int artist) {

        var sql =
                """
                    SELECT x.* FROM public.track_app x
                    WHERE artist_id = :artist and enabled = true
                    order by elo desc
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artist", artist)
                                .mapToBean(Song.class)
                                .list());
    }

    public Integer countAllByArtistId(int artistId) {
        var sql =
                """
                SELECT count(*) FROM track_app
                WHERE artist_id = :artistId
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artistId", artistId)
                                .mapTo(Integer.class)
                                .one());
    }

    public Song encontrarSong(int songId, Integer artistId) {
        var sql =
                """
                    SELECT * FROM public.track_app
                    WHERE song_id = :songId and artist_id = :artistId and enabled = true
                """;
        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("songId", songId)
                                .bind("artistId", artistId)
                                .mapToBean(Song.class)
                                .one());
    }

    public List<Results> findAllByArtistId(int artistId) {
        var sql =
                """
                    SELECT * FROM results_app WHERE artist_id = :artistId
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artistId", artistId)
                                .mapToBean(Results.class)
                                .list());
    }

    public List<Song> findAllByArtistIdOrderBySongIdAsc(int artistId) {
        var sql =
                """
                    select * from public.track_app
                    where artist_id = :artistId
                    order by song_id asc
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artistId", artistId)
                                .mapToBean(Song.class)
                                .list());
    }

    public void saveAllListaSongs(List<Song> listaCanciones) {
        PreparedBatch batch;
        try (Handle handle = jdbi.open()) {
            String sql =
                    """
                                 UPDATE track_app
                                 SET elo = :elo
                                 WHERE song_id = :songId and artist_id = :artistId;
                             """;
            batch = handle.prepareBatch(sql);

            for (Song i : listaCanciones) {
                batch.bind("songId", i.getSongId())
                        .bind("artistId", i.getArtistId())
                        .bind("elo", i.getElo())
                        .add();
            }
            batch.execute();
        }
    }

    public Integer incrementarWinsX(int matchId, int artist) {

        String sql =
                """
                        UPDATE results_app
                        SET wins_x = wins_x + 1
                        WHERE match_id = :matchId AND artist_id = :artistId
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public Integer incrementarWinsY(int matchId, int artist) {

        String sql =
                """
                        UPDATE results_app
                        SET wins_y=wins_y + 1
                        WHERE match_id = :matchId AND artist_id = :artistId
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public Integer incrementarDraws(int matchId, int artist) {

        String sql =
                """
                        UPDATE results_app
                        SET draws= draws+1
                        WHERE match_id = :matchId AND artist_id = :artistId
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public Integer incrementarSkipped(int matchId, int artist) {

        String sql =
                """
                        UPDATE results_app
                        SET skipped=skipped+1
                        WHERE match_id = :matchId AND artist_id = :artistId
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public Integer voteLogSave(VoteLog voteLog) {
        String sql =
                """
                        INSERT INTO log_app
                        (match_id, vote, ip_addr, instant, artist_id)
                        values (:matchId, :vote, :ipAddr, :instant, :artistId);
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("matchId", voteLog.getMatchId())
                                .bind("vote", voteLog.getVote())
                                .bind("ipAddr", voteLog.getIpAddr())
                                .bind("instant", voteLog.getTimestamp())
                                .bind("artistId", voteLog.getArtistId())
                                .execute());
    }

    public int obtenerMax(int artistId) {
        String sql =
                """
                        select max(u.id.matchId)
                        from results_app
                        where artist_id = :artistId
                    """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artist_id", artistId)
                                .mapTo(Integer.class)
                                .one());
    }

    public void saveAll(List<Results> lista) {}
}
