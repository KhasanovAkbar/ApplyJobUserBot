package univ.tuit.applyjobuserbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    private Integer candidateId;
    private Long userId;
    private String jobId = "Register";
    private int isJobId = 0;
    private String username;
    private String name = "Register";
    private int isName = 0;
    private String age = "Register";
    private int isAge = 0;
    private String phoneNumber = "Register";
    private int isPhone = 0;
    private String state = "Register";
    private String filePath = "Register";
    private int isFilePath = 0;
    private String token = "Register";
    private String registrationTime;
    private int isRequirement = 0;
}
