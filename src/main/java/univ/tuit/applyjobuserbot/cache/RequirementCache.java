package univ.tuit.applyjobuserbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;
import univ.tuit.applyjobuserbot.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class RequirementCache implements Cache<Requirement> {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public void add(Requirement requirement) {
    }

    @Override
    public void update(Requirement requirement) {
        //with function works in BotUserCache
    }

    @Override
    public Requirement findBy(Long id, Integer sequence) {
        //with function works in BotUserCache
        return null;
    }

    @Override
    public List<Requirement> getAll() {
        //with function works in JobCache
        return null;
    }

    @Override
    public Requirement findByJobId(String id) {
        //this functions works for JobCache
        return null;
    }

    @Override
    public List<Requirement> findByJob(Jobs jobs) {
        return requirementRepository.findByJob(jobs);
    }
}
