/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.Hibernate;

@Entity
@Table(name = "results_app")
public class Results {
    @EmbeddedId private ResultsId id;

    @Column(name = "wins_x", nullable = false)
    private Integer winsX;

    @Column(name = "wins_y", nullable = false)
    private Integer winsY;

    @Column(name = "draws", nullable = false)
    private Integer empates;

    @Column(name = "skipped", nullable = false)
    private Integer skipped;

    @Column(name = "track_x", nullable = false)
    private Integer trackX;

    @Column(name = "track_y", nullable = false)
    private Integer trackY;

    public Results(int matchId, int winsX, int winsY, int empates, int skipped, int artistId, int trackX, int trackY) {
        this.winsX = winsX;
        this.winsY = winsY;
        this.empates = empates;
        this.skipped = skipped;
        this.id = new ResultsId(matchId, artistId);
        this.trackX = trackX;
        this.trackY = trackY;
    }

    public Results() {}

    public ResultsId getId() {
        return id;
    }

    public void setId(ResultsId id) {
        this.id = id;
    }

    public Integer getWinsX() {
        return winsX;
    }

    public void setWinsX(Integer winsX) {
        this.winsX = winsX;
    }

    public Integer getWinsY() {
        return winsY;
    }

    public void setWinsY(Integer winsY) {
        this.winsY = winsY;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer draws) {
        this.empates = draws;
    }

    public Integer getSkipped() {
        return skipped;
    }

    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    public int getMatchId() {
        return this.id.getMatchId();
    }

    public Integer getTrackX() {
        return trackX;
    }

    public void setTrackX(Integer trackX) {
        this.trackX = trackX;
    }

    public Integer getTrackY() {
        return trackY;
    }

    public void setTrackY(Integer trackY) {
        this.trackY = trackY;
    }

    @Embeddable
    public static class ResultsId implements Serializable {
        private static final long serialVersionUID = -1522298751762847986L;

        @Column(name = "match_id", nullable = false)
        private Integer matchId;

        @Column(name = "artist_id", nullable = false)
        private Integer artistId;

        public ResultsId(Integer matchId, Integer artistId) {
            this.matchId = matchId;
            this.artistId = artistId;
        }

        public ResultsId() {}

        public Integer getMatchId() {
            return matchId;
        }

        public void setMatchId(Integer matchId) {
            this.matchId = matchId;
        }

        public Integer getArtistId() {
            return artistId;
        }

        public void setArtistId(Integer artistId) {
            this.artistId = artistId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            ResultsId entity = (ResultsId) o;
            return Objects.equals(this.artistId, entity.artistId)
                    && Objects.equals(this.matchId, entity.matchId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(artistId, matchId);
        }
    }
}
