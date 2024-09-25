package com.riwi.sitekeeper.dtos.requests;

import com.riwi.sitekeeper.enums.ReportStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportSeederReq {


        private String name;

        private String description;

        private Boolean isEvent;

        private Long topicId;

        private LocalDateTime theDate;

        private String image;

        private Long createdBy;

        private Long UpdatedBy;

        private Long spaceId;

        private ReportStatus status;
}

