package univ.tuit.applyjobuserbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Candidate;
import univ.tuit.applyjobuserbot.store.jpo.CandidateJpo;
import univ.tuit.applyjobuserbot.store.repo.CandidateRepository;

import java.util.List;

@Repository
public class CandidateStoreLogic implements CandidateStore<Candidate> {

    @Autowired
    CandidateRepository candidateRepository;

    @Override
    public void add(Candidate candidate) {
        CandidateJpo candidateJpo = CandidateJpo.fromDomain(candidate);
        candidateRepository.save(candidateJpo);
    }

    @Override
    public void update(Candidate candidate) {
        CandidateJpo candidateJpo = CandidateJpo.fromDomain(candidate);
        candidateRepository.save(candidateJpo);
    }

    @Override
    public Candidate findBy(Long id, Integer sequence) {
        CandidateJpo byUserIdAndApplyId = candidateRepository.findByUserIdAndCandidateId(id, sequence);
        return CandidateJpo.toDomain(byUserIdAndApplyId);
    }

    @Override
    public List<Candidate> getAll() {
        List<CandidateJpo> all = candidateRepository.getAll();
        return CandidateJpo.toDomain(all);
    }

    @Override
    public List<Candidate> findByJobId(String s) {
        List<CandidateJpo> byJobId = candidateRepository.findByJobId(s);
        return CandidateJpo.toDomain(byJobId);
    }


}
