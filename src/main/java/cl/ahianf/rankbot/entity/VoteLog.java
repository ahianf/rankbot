package cl.ahianf.rankbot.entity;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "log", schema = "deathgrips")
public class VoteLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "match_id")
    private int matchId;

    @Column(name = "vote")
    private int vote;

    @Column(name = "ip_addr")
    private String ipAddress;

    @Column(name = "timestamp")
    private Instant instant;

    public VoteLog(int matchId, int vote, String ipAddress, Instant instant) {
        this.matchId = matchId;
        this.vote = vote;
        this.ipAddress = ipAddress;
        this.instant = instant;
    }

    public VoteLog() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
