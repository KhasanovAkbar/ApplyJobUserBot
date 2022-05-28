package univ.tuit.applyjobuserbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.store.jpo.JobsJpo;
import univ.tuit.applyjobuserbot.store.jpo.RequirementJpo;

import java.util.List;
import java.util.Optional;

public interface RequirementRepository extends JpaRepository<RequirementJpo, Integer> {

    List<RequirementJpo> findByJob(JobsJpo jobsJpo);

}
