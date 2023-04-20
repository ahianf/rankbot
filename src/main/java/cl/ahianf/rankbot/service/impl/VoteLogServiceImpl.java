/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.service.impl;

import cl.ahianf.rankbot.entity.VoteLog;
import cl.ahianf.rankbot.repository.VoteLogRepository;
import cl.ahianf.rankbot.service.VoteLogService;
import org.springframework.stereotype.Service;

@Service
public class VoteLogServiceImpl implements VoteLogService {

    private final VoteLogRepository repository;

    public VoteLogServiceImpl(VoteLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(VoteLog theVoteLog) {
        repository.save(theVoteLog);
    }
}
