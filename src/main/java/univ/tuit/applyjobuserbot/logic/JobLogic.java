package univ.tuit.applyjobuserbot.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.services.JobService;
import univ.tuit.applyjobuserbot.store.JobStore;
import univ.tuit.applyjobuserbot.store.RequirementStore;

import java.util.List;

@Service
@Slf4j
public class JobLogic implements JobService<Jobs> {

    @Autowired
    JobStore<Jobs> jobStore;

    @Autowired
    RequirementStore requirementStore;

    @Override
    public Jobs add(Jobs jobs) {
        Jobs save;
        if (jobs.getUserId() != null) {
            save = jobStore.create(jobs);
            log.info("job was created: " + jobs.getUserId());
        } else {
            log.error("job wasn't found");
            throw new IllegalArgumentException("No id");
        }
        return save;
    }

    @Override
    public Jobs update(Jobs jobs) {
        Jobs add;
        if (jobs.getUserId() != null) {
            Jobs byUserId = jobStore.findByUserIdAndId(jobs.getUserId(), jobs.getId());
            if (byUserId != null && jobs.getId().equals(byUserId.getId())) {
                log.info("job was updated: " + jobs.getUserId());

                byUserId.setId(jobs.getId());
                byUserId.setCompanyName(jobs.getCompanyName());
                byUserId.setIsCompanyName(jobs.getIsCompanyName());
                byUserId.setTechnology(jobs.getTechnology());
                byUserId.setIsTechnology(jobs.getIsTechnology());
                byUserId.setTerritory(jobs.getTerritory());
                byUserId.setIsTerritory(jobs.getIsTerritory());
                byUserId.setJobId(jobs.getJobId());
                byUserId.setState(jobs.getState());
                byUserId.setIsRequirements(jobs.getIsRequirements());
                add = jobStore.update(byUserId);

            } else {
                log.warn("job wasn't updated: " + jobs.getUserId());
                add = jobStore.update(jobs);
            }
        } else {
            log.error("job not found");
            throw new IllegalArgumentException("No id");
        }
        return add;
    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        log.info("job found by userId and sequence: " + id);
        return jobStore.findBy(id, sequence);
    }

    @Override
    public List<Jobs> getAll() {
        log.info("all jobs");
        return jobStore.getAll();
    }

    @Override
    public List<Jobs> findByUserId(Long id) {
        log.info("return jobs with userId : " +id);
        return jobStore.findByUserId(id);
    }

    @Override
    public Jobs findByJobId(String id) {
        return jobStore.findByJobId(id);
    }
}
