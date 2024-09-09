package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.entitites.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRes {

    private Long id;

    private String name;

    private String description;

    private Boolean isEvent;

    private String image;

    private TopicEntity topicId;

    private LocalDateTime theDate;

    private SpaceEntity spaceId;
}
