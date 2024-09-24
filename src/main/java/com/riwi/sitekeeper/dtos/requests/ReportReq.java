package com.riwi.sitekeeper.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Is Event is required")
    private Boolean isEvent;

    @NotBlank(message = "Topic is required")
    private Long topicId;

    @NotBlank(message = "The Date is required")
    private LocalDateTime theDate;

    @NotBlank(message = "Space is required")
    private Long spaceId;

    private Long objectId;

}
