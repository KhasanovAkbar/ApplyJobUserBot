package univ.tuit.applyjobuserbot.services;

import java.util.List;

public interface CandidateService<T> {

    void apply(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByJobId(String id);

}
