package univ.tuit.applyjobuserbot.cache;


import univ.tuit.applyjobuserbot.domain.Jobs;

import java.util.List;

public interface Cache<T> {

    void add(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    T findByJobId(String id);

    List<T> findByJob(Jobs jobs);

}
