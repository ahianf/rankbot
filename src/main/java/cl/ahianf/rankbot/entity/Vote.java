package cl.ahianf.rankbot.entity;

public class Vote {
    private int vote;
    private int token;

    public Vote(int matchId, int vote, int token) {
        this.vote = vote;
        this.token = token;
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
