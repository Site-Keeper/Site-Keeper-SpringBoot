package com.riwi.sitekeeper.dtos.nest.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRes {

    private Long statusCode;

    private ValidationUserRes data;

}
