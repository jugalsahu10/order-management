package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.dto.JobRequest;
import com.docsehr.flowerhub.model.mongo.Notification;
import com.docsehr.flowerhub.model.mysql.Job;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired private UserService userService;
    @Autowired private JobRepository jobRepository;
    @Autowired private NotificationService notificationService;

    public void postJob(JobRequest request) {
        Job job = jobRepository.findById(request.getJobId()).orElseThrow(() -> new RuntimeException("Job not found"));

        // accept job application logic

        // notification logic
        User jobPoster = userService.getUser(job.getUserId());
        notificationService.sendNotification(jobPoster, Notification.NotificationType.INSTANT, "Applicant has sent an job request");

        User applicant = userService.getUser(request.getApplicantEmail());
        notificationService.sendNotification(applicant, Notification.NotificationType.INSTANT, "You have successfully applied for the job");
    }
}

