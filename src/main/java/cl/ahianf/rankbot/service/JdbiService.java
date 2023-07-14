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

    public void incrementarWinsX(int matchId, int artist) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped)
                    VALUES (:matchId, :artistId, 1, 0, 0, 0)
                    ON CONFLICT (match_id, artist_id) DO UPDATE SET wins_x = results_app.wins_x + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public void incrementarWinsY(int matchId, int artist) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped)
                    VALUES (:matchId, :artistId, 0, 1, 0, 0)
                    ON CONFLICT (match_id, artist_id) DO UPDATE SET wins_y = results_app.wins_y + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public void incrementarDraws(int matchId, int artist) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped)
                    VALUES (:matchId, :artistId, 0, 0, 1, 0)
                    ON CONFLICT (match_id, artist_id) DO UPDATE SET draws = results_app.draws + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public void incrementarSkipped(int matchId, int artist) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped)
                    VALUES (:matchId, :artistId, 0, 0, 0, 1)
                    ON CONFLICT (match_id, artist_id) DO UPDATE SET skipped = results_app.skipped + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .execute());
    }

    public void voteLogSave(VoteLog voteLog) {
        String sql =
                """
                            INSERT INTO log_app
                            (match_id, vote, ip_addr, instant, artist_id)
                            values (:matchId, :vote, :ipAddr, :instant, :artistId);
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("matchId", voteLog.getMatchId())
                                .bind("vote", voteLog.getVote())
                                .bind("ipAddr", voteLog.getIpAddr())
                                .bind("instant", voteLog.getTimestamp())
                                .bind("artistId", voteLog.getArtistId())
                                .execute());
    }
}
