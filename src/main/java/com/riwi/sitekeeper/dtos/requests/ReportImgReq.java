package com.riwi.sitekeeper.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportImgReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Is Event is required")
    private Boolean isEvent;

    @NotBlank(message = "Topic is required")
    private Long topicId;

    private LocalDateTime theDate;

    @NotBlank(message = "Image is required")
    private String image;

    @NotBlank(message = "Space is required")
    private Long spaceId;
}
