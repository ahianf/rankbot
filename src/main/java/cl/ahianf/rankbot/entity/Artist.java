/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @Column(name = "artist_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    //    @OneToMany(mappedBy = "artist")
    //    private Set<Song> songs = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Artist{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    //    public Set<Song> getSongs() {
    //        return songs;
    //    }
    //
    //    public void setSongs(Set<Song> songs) {
    //        this.songs = songs;
    //    }
}
