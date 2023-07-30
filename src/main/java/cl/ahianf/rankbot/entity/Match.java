/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
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

    @Override
    public String toString() {
        return "song A: "
                + songA
                + ", song B: "
                + songB
                + ", matchId: "
                + matchId
                + ", token: "
                + token;
    }
}
