/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "track_app")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "song_id")
    private Integer songId;

    @Column(name = "title", length = 10485760)
    private String title;

    @Column(name = "album", length = 10485760)
    private String album;

    @Column(name = "artist", length = 10485760)
    private String artist;

    @Column(name = "artist_id", nullable = false)
    private Integer artistId;

    @Column(name = "elo")
    private Double elo;

    @Column(name = "art_url")
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

    @Override
    public String toString() {
        return "Song{" +
                ", songId=" + songId +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
