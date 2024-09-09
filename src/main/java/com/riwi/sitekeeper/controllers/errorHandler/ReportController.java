package com.riwi.sitekeeper.controllers.errorHandler;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.services.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@Tag(name = "Movies", description = "Reports Controller")
public class ReportController {

    @Autowired
    private ReportService reportService;

    public ResponseEntity<ReportEntity> create(@RequestBody @Valid ReportReq report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }
}
