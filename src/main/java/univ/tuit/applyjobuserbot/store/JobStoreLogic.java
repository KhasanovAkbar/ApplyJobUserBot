package univ.tuit.applyjobuserbot.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.store.jpo.JobsJpo;
import univ.tuit.applyjobuserbot.store.repo.JobRepository;
import univ.tuit.applyjobuserbot.store.repo.RequirementRepository;

import java.util.List;

@Repository
@Slf4j
public class JobStoreLogic implements JobStore<Jobs> {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Jobs create(Jobs jobs) {
        JobsJpo jobsJpo = JobsJpo.fromDomain(jobs);
        JobsJpo save = jobRepository.save(jobsJpo);
        return JobsJpo.toDomain(save);
    }

    @Override
    public Jobs update(Jobs jobs) {
        JobsJpo jobsJpo = JobsJpo.fromDomain(jobs);
        JobsJpo save = jobRepository.save(jobsJpo);
        return JobsJpo.toDomain(save);
    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        JobsJpo byUserIdAndId = jobRepository.findByUserIdAndId(id, sequence);
        return JobsJpo.toDomain(byUserIdAndId);
    }

    @Override
    public List<Jobs> getAll() {
        List<JobsJpo> all = jobRepository.getAll();
        return JobsJpo.toDomain(all);
    }


    @Override
    public List<Jobs> findByUserId(Long id) {
        List<JobsJpo> byUserId = jobRepository.findByUserId(id);
        return JobsJpo.toDomain(byUserId);
    }

    @Override
    public Jobs findByUserIdAndId(Long userId, Integer id) {
        JobsJpo byUserIdAndId = jobRepository.findByUserIdAndId(userId, id);
        return JobsJpo.toDomain(byUserIdAndId);
    }

    @Override
    public Jobs findByJobId(String id) {
        Jobs jobs;
        JobsJpo byJobId = jobRepository.findByJobId(id);
        if (byJobId != null) {
            jobs = JobsJpo.toDomain(byJobId);
            log.info("job found"+jobs.getJobId());
        } else {
            jobs = null;
            log.error("job not found");
        }
        return jobs;
    }
}
