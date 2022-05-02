package cl.ahianf.rankbot.entity;

public class Vote {
    private int matchId;
    private int vote;
    private int token;

    public Vote(int matchId, int vote, int token) {
        this.matchId = matchId;
        this.vote = vote;
        this.token = token;
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

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
