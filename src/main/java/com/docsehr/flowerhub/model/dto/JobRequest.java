package com.docsehr.flowerhub.model.dto;

import lombok.Data;


@Data
public class JobRequest {
    private Long jobId;
    private String applicantName;
    private String applicantEmail;
    private String coverLetter;
}



