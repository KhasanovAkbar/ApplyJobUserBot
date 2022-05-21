package univ.tuit.applyjobuserbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.repo.JobRepository;
import univ.tuit.applyjobuserbot.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class JobCache implements Cache<Jobs> {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public void add(Jobs jobs) {

    }

    @Override
    public void update(Jobs jobs) {
    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        return jobRepository.findByUserIdAndId(id, sequence);
    }

    @Override
    public List<Jobs> getAll() {
        return null;
    }

    @Override
    public Jobs findByJobId(String id) {
        return jobRepository.findByJobId(id);
    }

    @Override
    public List<Jobs> findByJob(Jobs jobs) {
        //this functions works for RequirementCache
        return null;
    }
}
