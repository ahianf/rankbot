/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.entity.Artist;
import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.entity.Song;
import cl.ahianf.rankbot.entity.VoteLog;
import java.util.List;
import java.util.UUID;
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
        String sql = "SELECT * FROM public.artist_credit_musicbrainz";
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

    public List<Song> obtenerCancionesByUser(int artistId, UUID uuid) {

        var sql =
                """
                        SELECT
                            ta.song_id,
                            ta.title,
                            ta.album,
                            ta.artist,
                            ta.artist_id,
                            COALESCE(sea.elo, 1000.0) AS elo,
                            ta.art_url,
                            ta.enabled
                        FROM public.track_app ta
                        LEFT JOIN public.songs_elo_app sea ON sea.song_id = ta.song_id AND sea.user_uuid = :uuid AND sea.artist_id = ta.artist_id
                        where ta.artist_id = :artistId
                        ORDER BY elo desc;
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artistId", artistId)
                                .bind("uuid", uuid)
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

    public List<Results> findAllByArtistIdAndUserUuid(int artistId, UUID uuid) {
        var sql =
                """
                        SELECT * FROM results_app WHERE artist_id = :artistId and user_uuid = :uuid
                """;

        return jdbi.withHandle(
                handle ->
                        handle.createQuery(sql)
                                .bind("artistId", artistId)
                                .bind("uuid", uuid)
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

    public void saveAllListaSongsPerUser(List<Song> listaCanciones, UUID uuid) {
        PreparedBatch batch;
        try (Handle handle = jdbi.open()) {
            String sql =
                    """
                                INSERT INTO songs_elo_app (song_id, artist_id, user_uuid, elo)
                                VALUES (:songId, :artistId, :uuid, :elo)
                                ON CONFLICT (song_id, artist_id, user_uuid) DO UPDATE SET elo = :elo;
                    """;

            batch = handle.prepareBatch(sql);

            for (Song i : listaCanciones) {
                batch.bind("songId", i.getSongId())
                        .bind("artistId", i.getArtistId())
                        .bind("elo", i.getElo())
                        .bind("uuid", uuid)
                        .add();
            }
            batch.execute();
        }
    }

    public void incrementarWinsXByUser(int matchId, int artist, UUID uuid) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped, user_uuid)
                    VALUES (:matchId, :artistId, 1, 0, 0, 0, :uuid)
                    ON CONFLICT (match_id, artist_id, user_uuid) DO UPDATE SET wins_x = results_app.wins_x + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .bind("uuid", uuid)
                                .execute());
    }

    public void incrementarWinsYByUser(int matchId, int artist, UUID uuid) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped, user_uuid)
                    VALUES (:matchId, :artistId, 0, 1, 0, 0, :uuid)
                    ON CONFLICT (match_id, artist_id, user_uuid) DO UPDATE SET wins_y = results_app.wins_y + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .bind("uuid", uuid)
                                .execute());
    }

    public void incrementarDrawsByUser(int matchId, int artist, UUID uuid) {

        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped, user_uuid)
                    VALUES (:matchId, :artistId, 0, 0, 1, 0, :uuid)
                    ON CONFLICT (match_id, artist_id, user_uuid) DO UPDATE SET draws = results_app.draws + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .bind("uuid", uuid)
                                .execute());
    }

    public void incrementarSkippedByUser(int matchId, int artist, UUID uuid) {
        String sql =
                """
                    INSERT INTO results_app (match_id, artist_id, wins_x, wins_y, draws, skipped, user_uuid)
                    VALUES (:matchId, :artistId, 0, 0, 0, 1, :uuid)
                    ON CONFLICT (match_id, artist_id, user_uuid) DO UPDATE SET skipped = results_app.skipped + 1;
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("artistId", artist)
                                .bind("matchId", matchId)
                                .bind("uuid", uuid)
                                .execute());
    }

    public void voteLogSave(VoteLog voteLog) {
        String sql =
                """
                            INSERT INTO log_app
                            (match_id, vote, ip_addr, instant, artist_id, user_uuid)
                            values (:matchId, :vote, :ipAddr, :instant, :artistId, :uuid);
                """;

        jdbi.withHandle(
                handle ->
                        handle.createUpdate(sql)
                                .bind("matchId", voteLog.getMatchId())
                                .bind("vote", voteLog.getVote())
                                .bind("ipAddr", voteLog.getIpAddr())
                                .bind("instant", voteLog.getTimestamp())
                                .bind("artistId", voteLog.getArtistId())
                                .bind("uuid", voteLog.getUuid())
                                .execute());
    }
}
