package com.riwi.sitekeeper.dtos.requests;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostObjectsReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image is required")
    private String image;

    @NotNull(message = "Space is required")
    private Long spaceId;

    @NotNull(message = "Status is required")
    private LostObjectsStatus status;

}
