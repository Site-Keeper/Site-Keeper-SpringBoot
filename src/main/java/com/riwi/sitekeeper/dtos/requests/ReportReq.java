package com.riwi.sitekeeper.dtos.requests;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.entitites.TopicEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Is Event is required")
    private Boolean isEvent;

    @NotBlank(message = "Image is required")
    private String image;

    @NotBlank(message = "Topic is required")
    private TopicEntity topicId;

    @NotBlank(message = "The Date is required")
    private LocalDateTime theDate;

    @NotBlank(message = "Space is required")
    private SpaceEntity spaceId;

}
