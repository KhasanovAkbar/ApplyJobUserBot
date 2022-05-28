package univ.tuit.applyjobuserbot.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Applied;
import univ.tuit.applyjobuserbot.store.jpo.AppliedJpo;
import univ.tuit.applyjobuserbot.store.repo.AppliedRepository;

import java.util.List;

@Repository
@Slf4j
public class ApplyStoreLogic implements ApplyStore<Applied> {

    @Autowired
    AppliedRepository appliedRepository;

    @Override
    public Applied create(Applied applied) {
        AppliedJpo save = appliedRepository.save(AppliedJpo.fromDomain(applied));
        return AppliedJpo.toDomain(save);
    }

    @Override
    public Applied update(Applied applied) {
        AppliedJpo save = appliedRepository.save(AppliedJpo.fromDomain(applied));
        return AppliedJpo.toDomain(save);
    }

    @Override
    public List<Applied> getAll() {
        List<AppliedJpo> all = appliedRepository.getAll();
        return AppliedJpo.toDomain(all);
    }

    @Override
    public List<Applied> findByJobIdAndCandidateId(String jobId, Long candidateId) {
        List<AppliedJpo> byJobIdAndCandidateId = appliedRepository.findByJobIdAndCandidateId(jobId, candidateId);
        return AppliedJpo.toDomain(byJobIdAndCandidateId);
    }
}
