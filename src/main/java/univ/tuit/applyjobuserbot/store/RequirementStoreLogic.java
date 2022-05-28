package univ.tuit.applyjobuserbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;
import univ.tuit.applyjobuserbot.store.jpo.JobsJpo;
import univ.tuit.applyjobuserbot.store.jpo.RequirementJpo;
import univ.tuit.applyjobuserbot.store.repo.RequirementRepository;

import java.util.List;

@Repository
public class RequirementStoreLogic implements RequirementStore<Requirement> {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Requirement create(Requirement t) {
        RequirementJpo requirementJpo = RequirementJpo.fromDomain(t);
        RequirementJpo save = requirementRepository.save(requirementJpo);
        return RequirementJpo.toDomain(save);
    }

    @Override
    public Requirement update(Requirement requirement) {
        RequirementJpo requirementJpo = RequirementJpo.fromDomain(requirement);
        RequirementJpo save = requirementRepository.save(requirementJpo);
        return RequirementJpo.toDomain(save);
    }

    @Override
    public List<Requirement> findByJob(Jobs job) {
        JobsJpo jobsJpo = JobsJpo.fromDomain(job);
        List<RequirementJpo> byJob = requirementRepository.findByJob(jobsJpo);
        return RequirementJpo.toDomain(byJob);
    }

    @Override
    public Requirement findById(Integer id) {
        RequirementJpo byId = requirementRepository.getById(id);
        return RequirementJpo.toDomain(byId);
    }
}
