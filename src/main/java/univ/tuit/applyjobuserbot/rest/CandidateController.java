package univ.tuit.applyjobuserbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.tuit.applyjobuserbot.domain.Candidate;
import univ.tuit.applyjobuserbot.helper.APIResponse;
import univ.tuit.applyjobuserbot.helper.ResponseBuilder;
import univ.tuit.applyjobuserbot.services.CandidateService;

import java.util.List;

@RestController
@RequestMapping("api/v1/apply")
public class CandidateController {

    @Autowired
    CandidateService<Candidate> candidateService;



    @GetMapping(value = "/{jobId}")
    ResponseEntity<APIResponse> findByJobId(@PathVariable String jobId) {
        List<Candidate> byJobId = candidateService.findByJobId(jobId);
        return ResponseBuilder.buildOK(byJobId, null, HttpStatus.OK);
    }
}
