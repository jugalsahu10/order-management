package com.docsehr.flowerhub.controller;

import com.docsehr.flowerhub.model.dto.JobRequest;
import com.docsehr.flowerhub.model.dto.ProductDTO;
import com.docsehr.flowerhub.model.mongo.Order;
import com.docsehr.flowerhub.service.JobService;
import com.docsehr.flowerhub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired private JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<Boolean> postJob(@RequestBody JobRequest request) {
        jobService.postJob(request);
        return ResponseEntity.ok(true);
    }
}

