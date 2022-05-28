package univ.tuit.applyjobuserbot.services;

import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;

import java.util.List;

public interface RequirementService {

    Requirement add(Requirement t);

    List<Requirement> findByJob(Jobs job);

    void update(Requirement r);


}
