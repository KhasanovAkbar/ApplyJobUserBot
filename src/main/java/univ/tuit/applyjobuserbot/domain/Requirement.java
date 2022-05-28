package univ.tuit.applyjobuserbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requirement {
    private Integer id;
    private String name;
    private Jobs job;
    private int status = 1;
}
