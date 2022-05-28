package univ.tuit.applyjobuserbot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {

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
}
