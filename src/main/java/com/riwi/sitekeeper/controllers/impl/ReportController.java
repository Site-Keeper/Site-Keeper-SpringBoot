package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Reports Controller")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    @Operation(summary = "Create a new report", description = "Creates a new report with the given details")
    @ApiResponse(responseCode = "201", description = "Report created successfully")
    public ResponseEntity<ReportRes> createReport(@RequestBody @Valid ReportReq report) {
        ReportRes createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all reports", description = "Retrieves a list of all reports")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reports")
    public ResponseEntity<List<ReportRes>> getAllReports() {
        List<ReportRes> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a report by ID", description = "Retrieves a report by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the report")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<ReportRes> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a report", description = "Updates an existing report with the given details")
    @ApiResponse(responseCode = "200", description = "Report updated successfully")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<ReportRes> updateReport(@PathVariable Long id, @RequestBody @Valid ReportReq report) {
        try {
            ReportRes updatedReport = reportService.updateReport(id, report);
            return ResponseEntity.ok(updatedReport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a report", description = "Deletes a report by its ID")
    @ApiResponse(responseCode = "204", description = "Report deleted successfully")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
