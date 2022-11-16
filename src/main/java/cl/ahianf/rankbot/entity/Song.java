/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "track_app")
@JsonIgnoreProperties({"id", "artistId", "songId"})
public class Song {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "song_id")
    private Integer songId;

    @Column(name = "title")
    private String title;

    @Column(name = "album")
    private String album;

    @Column(name = "artist")
    private String artist;

    @Column(name = "artist_id", nullable = false)
    private Integer artistId;

    @Column(name = "elo")
    private Double elo;

    @Column(name = "art_url", length = 40)
    private String artUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public Double getElo() {
        return elo;
    }

    public void setElo(Double elo) {
        this.elo = elo;
    }

    public String getArtUrl() {
        return artUrl;
    }

    @Override
    public String toString() {
        return title + ", " + album;
    }
}
