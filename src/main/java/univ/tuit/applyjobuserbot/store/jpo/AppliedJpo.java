package univ.tuit.applyjobuserbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import univ.tuit.applyjobuserbot.domain.Applied;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Apply")
public class AppliedJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyId")
    private Integer appliedId;

    private String jobId;

    private Long candidateId;

    private String status;

    private String registrationTime;

    private int requirementCount;

    public static AppliedJpo fromDomain(Applied apply) {

        AppliedJpo applyJpo = new AppliedJpo();

        applyJpo.setAppliedId(apply.getSequence());
        applyJpo.setJobId(apply.getJobId());
        applyJpo.setCandidateId(apply.getCandidateId());
        applyJpo.setStatus(apply.getStatus());
        applyJpo.setRegistrationTime(apply.getRegistrationTime());
        applyJpo.setRequirementCount(apply.getRequirementCount());

        return applyJpo;
    }

    public static Applied toDomain(AppliedJpo applyJpo) {
        Applied apply = new Applied();
            apply.setSequence(applyJpo.getAppliedId());
            apply.setJobId(applyJpo.getJobId());
            apply.setCandidateId(applyJpo.getCandidateId());
            apply.setStatus(applyJpo.getStatus());
            apply.setRegistrationTime(applyJpo.getRegistrationTime());
            apply.setRequirementCount(applyJpo.getRequirementCount());

        return apply;
    }

    public static List<Applied> toDomain(List<AppliedJpo> applyJpos) {
        List<Applied> list = new ArrayList<>();
        for (AppliedJpo job : applyJpos) {
            list.add(toDomain(job));
        }
        return list;
    }
}
