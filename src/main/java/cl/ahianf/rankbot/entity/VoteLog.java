/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import java.time.Instant;
import lombok.Data;

@Data
public class VoteLog {
    private Long id;
    private Integer matchId;
    private Integer vote;
    private String ipAddr;
    private Instant timestamp;
    private Integer artistId;

    public VoteLog(int matchId, int vote, String ipAddr, Instant timestamp, Integer artistId) {
        this.matchId = matchId;
        this.vote = vote;
        this.ipAddr = ipAddr;
        this.timestamp = timestamp;
        this.artistId = artistId;
    }
}
