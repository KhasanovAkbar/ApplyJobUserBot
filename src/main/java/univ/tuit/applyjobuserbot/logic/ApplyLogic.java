package univ.tuit.applyjobuserbot.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobuserbot.domain.Applied;
import univ.tuit.applyjobuserbot.services.ApplyService;
import univ.tuit.applyjobuserbot.store.ApplyStore;

import java.util.List;

@Service
@Slf4j
public class ApplyLogic implements ApplyService<Applied> {

    @Autowired
    ApplyStore<Applied> applyStore;


    @Override
    public Applied create(Applied applied) {
        Applied apply;
        if (applied.getCandidateId() != null) {
            apply = applyStore.create(applied);
            log.info("candidate was created: " + applied.getCandidateId());
        } else {
            log.error("candidate wasn't found to creating apply");
            throw new IllegalArgumentException("No id");
        }
        return apply;
    }

    @Override
    public Applied update(Applied applied) {
        Applied apply;
        if (applied.getCandidateId() != null) {
            List<Applied> byId = applyStore.findByJobIdAndCandidateId(applied.getJobId(), applied.getCandidateId());
            log.info("applied candidate was updated: " + applied.getCandidateId());
            Applied update = byId.get(byId.size() - 1);
            update.setSequence(applied.getSequence());
            update.setJobId(applied.getJobId());
            update.setCandidateId(applied.getCandidateId());
            update.setStatus(applied.getStatus());
            update.setRequirementCount(applied.getRequirementCount());
            apply = applyStore.update(update);
        } else {
            apply = null;
            log.error("candidate wasn't found for updating apply");
        }
        return apply;
    }

    @Override
    public List<Applied> getAll() {
        log.info("applied all candidates");
        return applyStore.getAll();
    }

    @Override
    public Applied findByJobIdAndCandidateId(String jobId, Long candidateId) {
        Applied applied ;
        List<Applied> byId = applyStore.findByJobIdAndCandidateId(jobId, candidateId);
        if (byId != null && byId.size() !=0) {
            log.info("return applied candidate with jobId : " +jobId);
            applied = byId.get(byId.size()-1);
        } else {
            applied = null;
            log.error("applied candidate not found");
        }
        return applied;
    }
}
