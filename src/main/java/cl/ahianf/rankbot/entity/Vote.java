package cl.ahianf.rankbot.entity;

public class Vote {

    private int matchId;

    private int vote;

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

    public Vote(int matchId, int vote) {
        this.matchId = matchId;
        this.vote = vote;
    }
}
