package cl.ahianf.rankbot.service;

import cl.ahianf.rankbot.dao.VoteLogRepository;
import cl.ahianf.rankbot.entity.VoteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteLogServiceImpl implements VoteLogService{

    @Autowired
    private VoteLogRepository repository;

    @Override
    public void save(VoteLog theVoteLog) {

        repository.save(theVoteLog);
    }
}
