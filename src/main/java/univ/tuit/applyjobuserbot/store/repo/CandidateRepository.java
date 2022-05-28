package univ.tuit.applyjobuserbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobuserbot.store.jpo.CandidateJpo;

import java.util.List;


public interface CandidateRepository extends JpaRepository<CandidateJpo, Long> {

    List<CandidateJpo> findByJobId(String jobId);

    @Query(value = "select a from CandidateJpo a order by a.candidateId desc")
    List<CandidateJpo> getAll();

    CandidateJpo findByUserIdAndCandidateId(Long userId, Integer id);

}
