package com.docsehr.flowerhub.model.mongo;

import com.docsehr.flowerhub.model.dto.ProductDTO;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "notifications")
@Data
public class Notification {
    @Id
    private String id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private NotificationType type = NotificationType.INSTANT;
    private String message;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    private LocalDateTime timestamp = LocalDateTime.now();

    public enum NotificationType {
        INSTANT,
        SCHEDULED
    }

    public enum Status {
        PENDING,
        SENT,
        FAILED
    }
}

