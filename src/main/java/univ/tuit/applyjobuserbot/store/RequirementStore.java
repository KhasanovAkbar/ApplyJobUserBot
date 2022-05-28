package univ.tuit.applyjobuserbot.store;

import univ.tuit.applyjobuserbot.domain.Jobs;

import java.util.List;

public interface RequirementStore<T> {

    T create(T t);

    T update(T t);

    List<T> findByJob(Jobs job);

    T findById(Integer id);

}
