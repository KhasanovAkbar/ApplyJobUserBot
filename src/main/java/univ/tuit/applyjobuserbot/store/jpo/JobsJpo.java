package univ.tuit.applyjobuserbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import univ.tuit.applyjobuserbot.domain.Jobs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jobs")
public class JobsJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Integer id;

    private Long userId;

    private String username;

    private String jobId;

    private String companyName;

    private int isCompanyName;

    private String technology;

    private int isTechnology;

    private String territory;

    private int isTerritory;

    private String state;

    private int isRequirements;

    private String registrationTime;

    public static JobsJpo fromDomain(Jobs jobs) {
        JobsJpo jobsJpo = new JobsJpo();

        jobsJpo.setId(jobs.getId());
        jobsJpo.setUsername(jobs.getUsername());
        jobsJpo.setJobId(jobs.getJobId());
        jobsJpo.setUserId(jobs.getUserId());

        jobsJpo.setCompanyName(jobs.getCompanyName());
        jobsJpo.setIsCompanyName(jobs.getIsCompanyName());

        jobsJpo.setTechnology(jobs.getTechnology());
        jobsJpo.setIsTechnology(jobs.getIsTechnology());

        jobsJpo.setTerritory(jobs.getTerritory());
        jobsJpo.setIsTerritory(jobs.getIsTerritory());

        jobsJpo.setState(jobs.getState());

        jobsJpo.setIsRequirements(jobs.getIsRequirements());

        jobsJpo.setRegistrationTime(jobs.getRegistrationTime());
        return jobsJpo;
    }

    public static Jobs toDomain(JobsJpo jobs) {
        Jobs job = new Jobs();

        job.setId(jobs.getId());
        job.setUsername(jobs.getUsername());
        job.setJobId(jobs.getJobId());
        job.setUserId(jobs.getUserId());

        job.setCompanyName(jobs.getCompanyName());
        job.setIsCompanyName(jobs.getIsCompanyName());

        job.setTechnology(jobs.getTechnology());
        job.setIsTechnology(jobs.getIsTechnology());

        job.setTerritory(jobs.getTerritory());
        job.setIsTerritory(jobs.getIsTerritory());

        job.setState(jobs.getState());

        job.setIsRequirements(jobs.getIsRequirements());

        job.setRegistrationTime(jobs.getRegistrationTime());
        return job;
    }

    public static List<Jobs> toDomain(List<JobsJpo> jobsJpos) {
        List<Jobs> list = new ArrayList<>();
        for (JobsJpo job : jobsJpos) {
            list.add(toDomain(job));
        }
        return list;
    }
}
