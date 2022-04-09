package cl.ahianf.rankbot.dao;

import cl.ahianf.rankbot.entity.VoteLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteLogRepository extends JpaRepository<VoteLog, Integer> {


}

