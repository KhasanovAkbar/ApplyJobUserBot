package univ.tuit.applyjobuserbot.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobuserbot.domain.Candidate;
import univ.tuit.applyjobuserbot.services.CandidateService;
import univ.tuit.applyjobuserbot.store.CandidateStore;

import java.util.List;

@Service
@Slf4j
public class CandidateLogic implements CandidateService<Candidate> {

    @Autowired
    CandidateStore<Candidate> candidateStore;

    @Override
    public void apply(Candidate candidate) {
        log.info("candidate was created: " + candidate.getUserId());
        candidateStore.add(candidate);
    }

    @Override
    public void update(Candidate candidate) {
        if (candidate.getUserId() != null) {
            Candidate byUserId = candidateStore.findBy(candidate.getUserId(), candidate.getCandidateId());
            if (byUserId != null && candidate.getCandidateId().equals(byUserId.getCandidateId())) {
                log.info("candidate was updated: " + candidate.getUserId());
                byUserId.setCandidateId(candidate.getCandidateId());

                byUserId.setJobId(candidate.getJobId());
                byUserId.setIsJobId(candidate.getIsJobId());

                byUserId.setName(candidate.getName());
                byUserId.setIsName(candidate.getIsName());

                byUserId.setAge(candidate.getAge());
                byUserId.setIsAge(candidate.getIsAge());

                byUserId.setPhoneNumber(candidate.getPhoneNumber());
                byUserId.setIsPhone(candidate.getIsPhone());

                byUserId.setState(candidate.getState());

                byUserId.setFilePath(candidate.getFilePath());
                byUserId.setIsFilePath(candidate.getIsFilePath());

                byUserId.setToken(candidate.getToken());
                byUserId.setIsRequirement(candidate.getIsRequirement());

                candidateStore.update(byUserId);
            } else {
                log.warn("candidate wasn't updated: " + candidate.getUserId());
                candidateStore.update(candidate);
            }
        } else {
            log.error("candidate not found");
            throw new NullPointerException("No id");
        }
    }

    @Override
    public Candidate findBy(Long id, Integer sequence) {
        log.info("candidate found by id: " + id);
        return candidateStore.findBy(id, sequence);
    }

    @Override
    public List<Candidate> getAll() {
        log.info("all candidates");
        return candidateStore.getAll();
    }

    @Override
    public List<Candidate> findByJobId(String id) {
        log.info("return candidates with jobId : " +id);
        return candidateStore.findByJobId(id);
    }

}
