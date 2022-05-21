package univ.tuit.applyjobuserbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobuserbot.domain.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Integer> {

    Jobs findByUserIdAndId(Long userId, Integer id);

    Jobs findByJobId(String id);

}
