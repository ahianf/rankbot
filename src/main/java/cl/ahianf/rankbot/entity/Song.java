/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRACK_APP")
@JsonIgnoreProperties({"id", "artistId", "songId"})
public class Song {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "SONG_ID")
    private Integer songId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ALBUM")
    private String album;

    @Column(name = "ARTIST")
    private String artist;

    @Column(name = "ARTIST_ID", nullable = false)
    private Integer artistId;

    @Column(name = "ELO")
    private Double elo;

    @Column(name = "ART_URL")
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

    public void setSongId(Integer songId) {
        this.songId = songId;
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

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
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

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }
}
