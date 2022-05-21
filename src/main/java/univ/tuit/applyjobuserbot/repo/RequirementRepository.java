package univ.tuit.applyjobuserbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

    List<Requirement> findByJob(Jobs jobs);
}
