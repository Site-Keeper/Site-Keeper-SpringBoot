package com.riwi.sitekeeper.dtos.nest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationReq {

    @NotBlank(message = "Entity is required")
    private String entity;

    @NotBlank(message = "Permissions is required")
    private String permissions;

}
