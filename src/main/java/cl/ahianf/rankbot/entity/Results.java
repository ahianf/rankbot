package cl.ahianf.rankbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "results", schema = "deathgrips")
public class Results {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "wins_x")
    private int winsX;

    @Column(name = "wins_y")
    private int winsY;

    @Column(name = "draws")
    private int draws;

    @Column(name = "skipped")
    private int skipped;
    public int getMatchId() {
        return matchId;
    }

    public Results() {
    }

    public Results(int winsX, int winsY, int draws, int skipped) {
        this.winsX = winsX;
        this.winsY = winsY;
        this.draws = draws;
        this.skipped = skipped;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getWinsX() {
        return winsX;
    }

    public void setWinsX(int winsX) {
        this.winsX = winsX;
    }

    public int getWinsY() {
        return winsY;
    }

    public void setWinsY(int winsY) {
        this.winsY = winsY;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return "Results{" +
                "matchId=" + matchId +
                ", winsX=" + winsX +
                ", winsY=" + winsY +
                ", draws=" + draws +
                ", skipped=" + skipped +
                '}';
    }
}