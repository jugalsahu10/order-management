package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.mongo.Notification;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired private NotificationRepository notificationRepository;

    @Async
    public void sendNotification(User user, Notification.NotificationType notificationType, String message) {
        Notification notification = createPendingEntry(user, notificationType);

        try {
            System.out.println("Send notification.");
            // Send notification logic here
            notification.setStatus(Notification.Status.SENT);
        } catch (Exception e) {
            notification.setStatus(Notification.Status.FAILED);
        }
        notificationRepository.save(notification);
    }

    private Notification createPendingEntry(User user, Notification.NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setType(notificationType);
        notification.setUserId(user.getId());
        return notificationRepository.save(notification);
    }
}

