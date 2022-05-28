package univ.tuit.applyjobuserbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;
import univ.tuit.applyjobuserbot.helper.APIResponse;
import univ.tuit.applyjobuserbot.helper.ResponseBuilder;
import univ.tuit.applyjobuserbot.services.RequirementService;

import java.util.List;

@RestController
@RequestMapping("api/v1/requirement")
public class RequirementController {

    @Autowired
    RequirementService requirementService;

    @PostMapping(value = "/create")
    ResponseEntity<APIResponse> create(@RequestBody Requirement requirement) {
        Requirement add = requirementService.add(requirement);
        return ResponseBuilder.buildOK(add, null, HttpStatus.OK);
    }

    @PostMapping(value = "/byJob")
    ResponseEntity<APIResponse> findByJob(@RequestBody Jobs jobs) {
        List<Requirement> byJob = requirementService.findByJob(jobs);
        return ResponseBuilder.buildOK(byJob, null, HttpStatus.OK);
    }
}
