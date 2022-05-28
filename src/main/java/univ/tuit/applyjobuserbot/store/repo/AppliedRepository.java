package univ.tuit.applyjobuserbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobuserbot.store.jpo.AppliedJpo;

import java.util.List;

public interface AppliedRepository extends JpaRepository<AppliedJpo, Integer> {

    @Query(value = "select a from AppliedJpo a order by a.appliedId desc")
    List<AppliedJpo> getAll();

    List<AppliedJpo> findByJobIdAndCandidateId(String jobId, Long candidateId);

}
