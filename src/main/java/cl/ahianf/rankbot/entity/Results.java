/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;
import lombok.Data;

@Data
public class Results {
    private Integer matchId;
    private Integer artistId;
    private Integer winsX;
    private Integer winsY;
    private Integer draws;
    private Integer skipped;
    private Integer trackX;
    private Integer trackY;
}
