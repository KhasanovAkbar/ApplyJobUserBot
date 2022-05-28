package univ.tuit.applyjobuserbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import univ.tuit.applyjobuserbot.domain.Candidate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Candidate")
public class CandidateJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyId")
    private Integer candidateId;

    private Long userId;

    private String jobId;

    private int isJobId;

    private String username;

    private String name;

    private int isName;

    private String age;

    private int isAge;

    private String phoneNumber;

    private int isPhone;

    private String state;

    private String filePath;

    private int isFilePath;

    private String token;

    private String registrationTime;

    private int isRequirement;

    public static List<Candidate> toDomain(List<CandidateJpo> candidateJpos) {
        List<Candidate> list = new ArrayList<>();
        for (CandidateJpo candidateJpo : candidateJpos) {
            list.add(toDomain(candidateJpo));
        }
        return list;
    }

    public static Candidate toDomain(CandidateJpo candidateJpo) {
        Candidate candidate = new Candidate();

        candidate.setCandidateId(candidateJpo.getCandidateId());
        candidate.setUserId(candidateJpo.getUserId());

        candidate.setJobId(candidateJpo.getJobId());
        candidate.setIsJobId(candidateJpo.getIsJobId());

        candidate.setUsername(candidateJpo.getUsername());
        candidate.setName(candidateJpo.getName());
        candidate.setIsName(candidateJpo.getIsName());

        candidate.setAge(candidateJpo.getAge());
        candidate.setIsAge(candidateJpo.getIsAge());

        candidate.setPhoneNumber(candidateJpo.getPhoneNumber());
        candidate.setIsPhone(candidateJpo.getIsPhone());

        candidate.setState(candidateJpo.getState());
        candidate.setFilePath(candidateJpo.getFilePath());
        candidate.setIsFilePath(candidateJpo.getIsFilePath());

        candidate.setToken(candidateJpo.getToken());
        candidate.setRegistrationTime(candidateJpo.getRegistrationTime());

        candidate.setIsRequirement(candidateJpo.getIsRequirement());
        return candidate;
    }

    public static CandidateJpo fromDomain(Candidate candidate) {
        CandidateJpo candidateJpo = new CandidateJpo();

        candidateJpo.setCandidateId(candidate.getCandidateId());
        candidateJpo.setUserId(candidate.getUserId());

        candidateJpo.setJobId(candidate.getJobId());
        candidateJpo.setIsJobId(candidate.getIsJobId());

        candidateJpo.setUsername(candidate.getUsername());
        candidateJpo.setName(candidate.getName());
        candidateJpo.setIsName(candidate.getIsName());

        candidateJpo.setAge(candidate.getAge());
        candidateJpo.setIsAge(candidate.getIsAge());

        candidateJpo.setPhoneNumber(candidate.getPhoneNumber());
        candidateJpo.setIsPhone(candidate.getIsPhone());

        candidateJpo.setState(candidate.getState());

        candidateJpo.setFilePath(candidate.getFilePath());
        candidateJpo.setIsFilePath(candidate.getIsFilePath());

        candidateJpo.setToken(candidate.getToken());
        candidateJpo.setRegistrationTime(candidate.getRegistrationTime());

        candidateJpo.setIsRequirement(candidate.getIsRequirement());
        return candidateJpo;
    }

}
