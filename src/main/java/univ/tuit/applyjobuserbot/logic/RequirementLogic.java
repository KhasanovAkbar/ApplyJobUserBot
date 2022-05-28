package univ.tuit.applyjobuserbot.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;
import univ.tuit.applyjobuserbot.services.RequirementService;
import univ.tuit.applyjobuserbot.store.RequirementStore;

import java.util.List;

@Service
@Slf4j
public class RequirementLogic implements RequirementService {

    @Autowired
    RequirementStore<Requirement> requirementStore;

    @Override
    public Requirement add(Requirement t) {
        Requirement requirement;
        if (t.getJob().getUserId() != null) {
            log.info("requirement was created: " + t.getJob().getUserId());
            requirement = requirementStore.create(t);
        } else {
            log.error("requirement wasn't found");
            throw new IllegalArgumentException("No id");
        }
        return requirement;
    }

    @Override
    public List<Requirement> findByJob(Jobs job) {
        log.info("return requirements with job: " + job.getJobId());
        return requirementStore.findByJob(job);
    }

    @Override
    public void update(Requirement r) {
        if (r.getId() != null) {
            Requirement byId = requirementStore.findById(r.getId());
            log.info("requirement was updated: " + r.getJob().getUserId());

            byId.setId(r.getId());
            byId.setJob(r.getJob());
            byId.setName(r.getName());
            byId.setStatus(r.getStatus());
            requirementStore.update(byId);
        } else {
            log.warn("job wasn't updated: " + r.getJob().getUserId());
            throw new IllegalArgumentException("No id");
        }
    }

}
