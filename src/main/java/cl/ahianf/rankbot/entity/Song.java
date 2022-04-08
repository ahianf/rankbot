package cl.ahianf.rankbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "songs", schema = "deathgrips")
public class Song {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int songId;

    @Column(name = "title")
    private String title;

    @Column(name = "album")
    private String album;

    @Column(name = "artist")
    private String artist;

    @Column(name = "art_url")
    private String artUrl;

    public Song(int songId, String title) {
        this.songId = songId;
        this.title = title;
    }

    public Song() {
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

    public int getSongId() {
        return songId;
    }

    public void setSongId(int song_id) {
        this.songId = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtUrl() {
        return artUrl;
    }

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }
}
