/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import lombok.Data;

@Data
public class Song {
    private Integer id;
    private Integer songId;
    private String title;
    private String album;
    private String artist;
    private Integer artistId;
    private Double elo;
    private String artUrl;
    private boolean enabled;


}
