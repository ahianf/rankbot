/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.repository;

import cl.ahianf.rankbot.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {}
