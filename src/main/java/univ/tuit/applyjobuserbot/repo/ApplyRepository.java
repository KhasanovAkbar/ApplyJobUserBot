package univ.tuit.applyjobuserbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobuserbot.domain.Apply;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Integer> {

    @Query(value = "select a from Apply a order by a.applyId desc")
    List<Apply> getAll();

    Apply findByUserIdAndApplyId(Long userId, Integer id);

}
