package com.riwi.sitekeeper.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Object Request DTO")
public class ObjectReq {

    @Schema(description = "Name of the object", example = "Chair")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Description of the object", example = "A comfortable office chair")
    @NotBlank(message = "Description is required")
    private String description;

    @Schema(description = "Image filename of the object", example = "chair.jpg")
    @NotBlank(message = "Image is required")
    private String image;

    @Schema(hidden = true)
    private Long spaceId;
}
