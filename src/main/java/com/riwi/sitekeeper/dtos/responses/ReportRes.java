package com.riwi.sitekeeper.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRes {

    private Long id;

    private String name;

    private String description;

    private Boolean isEvent;

    private Long topicId;

    private LocalDateTime theDate;

    private Long spaceId;
}
