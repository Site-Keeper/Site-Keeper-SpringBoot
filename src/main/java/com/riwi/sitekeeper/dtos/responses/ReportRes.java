package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.SpaceEntity;
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

    private String image;

    private Long topicId;

    private LocalDateTime theDate;

    private Long spaceId;
}
