package univ.tuit.applyjobuserbot.services;

import java.util.List;

public interface ApplyService<T> {

    T create(T t);

    T update(T t);

    List<T> getAll();

    T findByJobIdAndCandidateId(String jobId, Long candidateId);
}
