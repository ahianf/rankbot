/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("matchId")
public class Match {
    private Song songA;
    private Song songB;
    private int matchId;
    private long token;

    public Match(Song songA, Song songB, int matchId, long token) {
        this.songA = songA;
        this.songB = songB;
        this.matchId = matchId;
        this.token = token;
    }

    public Song getSongA() {
        return songA;
    }

    public void setSongA(Song songA) {
        this.songA = songA;
    }

    public Song getSongB() {
        return songB;
    }

    public void setSongB(Song songB) {
        this.songB = songB;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "song A: "
                + songA
                + "; song B: "
                + songB
                + "; matchId: "
                + matchId
                + ", token: "
                + token;
    }
}
