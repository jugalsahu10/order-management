package com.docsehr.flowerhub.crons;

import com.docsehr.flowerhub.model.mongo.Notification;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.service.JobService;
import com.docsehr.flowerhub.service.NotificationService;
import com.docsehr.flowerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CronJobs {

    @Autowired private JobService jobService;
    @Autowired private UserService userService;
    @Autowired private NotificationService notificationService;

    @Scheduled(cron = "0 46 14 * * ?")
    public void sendDailyUpdates() {
        User adminUser = userService.getUser("admin@jobs.com");
        notificationService.sendNotification(adminUser, Notification.NotificationType.SCHEDULED, "Today there has been 100 active applicants");
    }

    @Scheduled(cron = "* * * * * *")
    public void checkCron() {
        System.out.println("cron running...." + LocalDateTime.now());
    }
}
