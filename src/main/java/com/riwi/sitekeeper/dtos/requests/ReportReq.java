package com.riwi.sitekeeper.dtos.requests;

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

    @NotBlank(message = "Topic is required")
    private Long topicId;

    private LocalDateTime theDate;

    @NotBlank(message = "Space is required")
    private Long spaceId;

}
