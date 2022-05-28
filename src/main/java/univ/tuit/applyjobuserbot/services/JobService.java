package univ.tuit.applyjobuserbot.services;

import java.util.List;

public interface JobService<T> {

    T add(T t);

    T update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByUserId(Long id);

    T findByJobId(String id);

}
