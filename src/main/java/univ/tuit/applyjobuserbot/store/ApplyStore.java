package univ.tuit.applyjobuserbot.store;

import java.util.List;

public interface ApplyStore<T> {

    T create(T t);

    T update(T t);

    List<T> getAll();

    List<T> findByJobIdAndCandidateId(String jobId, Long candidateId);
}
