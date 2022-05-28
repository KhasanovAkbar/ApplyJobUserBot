package univ.tuit.applyjobuserbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.helper.APIResponse;
import univ.tuit.applyjobuserbot.helper.ResponseBuilder;
import univ.tuit.applyjobuserbot.services.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    @Autowired
    JobService<Jobs> jobService;

    @PostMapping(value = "/create")
    ResponseEntity<APIResponse> create(@RequestBody Jobs jobs) {
        Jobs add = jobService.add(jobs);
        return ResponseBuilder.buildOK(add, null, HttpStatus.OK);
    }
    @PostMapping(value = "/update")
    ResponseEntity<APIResponse> update(@RequestBody Jobs jobs) {
        Jobs add = jobService.update(jobs);
        return ResponseBuilder.buildOK(add, null, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{id}")
    ResponseEntity<APIResponse> findByUserIdAndId(@PathVariable Long userId, @PathVariable Integer id) {
        Jobs by = jobService.findBy(userId, id);
        return ResponseBuilder.buildOK(by, null, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
        ResponseEntity<APIResponse> getAll(){
            List<Jobs> all = jobService.getAll();
            return ResponseBuilder.buildOK(all, null, HttpStatus.OK);
        }

    @GetMapping(value = "/{userId}")
    ResponseEntity<APIResponse> findByUserId(@PathVariable Long userId) {
        List<Jobs> byUserId = jobService.findByUserId(userId);
        return ResponseBuilder.buildOK(byUserId, null, HttpStatus.OK);
    }
}
