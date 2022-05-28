package univ.tuit.applyjobuserbot.store;

import java.util.List;

public interface CandidateStore<T> {

    void add(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByJobId(String s);


}
