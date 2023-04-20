/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

public class Vote {
    private int vote;
    private long token;

    public Vote(int matchId, int vote, long token) {
        this.vote = vote;
        this.token = token;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "vote: " + vote + ", token: " + token;
    }
}
