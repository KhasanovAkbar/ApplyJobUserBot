package univ.tuit.applyjobuserbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequirementJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobId")
    private JobsJpo job;

    private int status;

    public static RequirementJpo fromDomain(Requirement requirement) {

        RequirementJpo requirementJpo = new RequirementJpo();
        requirementJpo.setId(requirement.getId());
        requirementJpo.setName(requirement.getName());
        requirementJpo.setStatus(requirement.getStatus());
        requirementJpo.setJob(JobsJpo.fromDomain(requirement.getJob()));
        return requirementJpo;
    }

    public static List<Requirement> toDomain(List<RequirementJpo> requirementJpos) {
        List<Requirement> list = new ArrayList<>();
        for (RequirementJpo requirementJpo : requirementJpos) {
            list.add(toDomain(requirementJpo));
        }
        return list;
    }

    public static Requirement toDomain(RequirementJpo requirementJpo) {

        Requirement requirement = new Requirement();
        requirement.setId(requirementJpo.getId());
        requirement.setName(requirementJpo.getName());
        requirement.setStatus(requirementJpo.getStatus());
        requirement.setJob(JobsJpo.toDomain(requirementJpo.getJob()));
        return requirement;
    }
}
