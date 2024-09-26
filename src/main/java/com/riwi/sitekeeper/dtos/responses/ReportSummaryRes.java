package com.riwi.sitekeeper.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryRes {

    private Long total;

    private Long approvedTotal;

    private Long rejectedTotal;
}
