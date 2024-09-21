package com.riwi.sitekeeper.dtos.nest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("permissions")
    private String permissions;

}
