package univ.tuit.applyjobuserbot.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobuserbot.domain.Apply;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.repo.ApplyRepository;

import java.util.List;

@Component
@Repository
@Slf4j
public class ApplyCache implements Cache<Apply> {

    @Autowired
    private ApplyRepository applyRepository;

    @Override
    public void add(Apply apply) {
        if (apply.getUserId() != null) {
            applyRepository.save(apply);
            log.warn("User was created");
        } else throw new NullPointerException("No id");
    }

    @Override
    public void update(Apply apply) {
        if (apply.getUserId() != null) {
            Apply byUserId = applyRepository.findByUserIdAndApplyId(apply.getUserId(), apply.getApplyId());
            if (byUserId != null && apply.getApplyId().equals(byUserId.getApplyId())) {
                log.info("User was updated");
                byUserId.setJobId(apply.getJobId());
                byUserId.setIsJobId(apply.isJobId());
                byUserId.setName(apply.getName());
                byUserId.setIsName(apply.isName());
                byUserId.setAge(apply.getAge());
                byUserId.setIsAge(apply.isAge());
                byUserId.setPhoneNumber(apply.getPhoneNumber());
                byUserId.setIsPhone(apply.isPhone());
                byUserId.setState(apply.getState());
                byUserId.setFilePath(apply.getFilePath());
                byUserId.setIsFilePath(apply.isFilePath());
                byUserId.setToken(apply.getToken());
                applyRepository.save(byUserId);
            } else {
                log.warn("User wasn't updated");
                applyRepository.save(apply);
            }
        } else throw new NullPointerException("No id");
    }

    @Override
    public Apply findBy(Long id, Integer sequence) {
        return applyRepository.findByUserIdAndApplyId(id, sequence);
    }

    @Override
    public List<Apply> getAll() {
        return applyRepository.getAll();
    }

    @Override
    public Apply findByJobId(String id) {
        //this functions works for JobCache
        return null;
    }

    @Override
    public List<Apply> findByJob(Jobs jobs) {
        //this functions works for RequirementCache
        return null;
    }
}
