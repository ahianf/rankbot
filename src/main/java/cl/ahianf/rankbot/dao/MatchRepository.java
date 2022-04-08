package cl.ahianf.rankbot.dao;


import cl.ahianf.rankbot.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Integer> {
}
