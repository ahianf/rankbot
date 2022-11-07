/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import java.time.Instant;
import javax.persistence.*;

@Entity
@Table(name = "LOG_APP")
public class VoteLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MATCH_ID", nullable = false)
    private Integer matchId;

    @Column(name = "VOTE", nullable = false)
    private Integer vote;

    @Column(name = "IP_ADDR", length = 16)
    private String ipAddr;

    @Column(name = "instant", nullable = false)
    private Instant timestamp;

    @Column(name = "ARTIST_ID", nullable = false)
    private Integer artistId;

    public VoteLog(int matchId, int vote, String ipAddr, Instant timestamp, Integer artistId) {
        this.matchId = matchId;
        this.vote = vote;
        this.ipAddr = ipAddr;
        this.timestamp = timestamp;
        this.artistId = artistId;
    }

    public VoteLog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
