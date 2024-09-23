package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.ReportSummaryRes;
import com.riwi.sitekeeper.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Reports Controller")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new report", description = "Creates a new Report")
    @ApiResponse(responseCode = "201", description = "Report successfully created")
    public ResponseEntity<?> createReport(
            @Parameter(description = "Name of the report") @RequestParam("name") String name,
            @Parameter(description = "Description of the report") @RequestParam("description") String description,
            @Parameter(description = "isEvent of the report") @RequestParam("isEvent") Boolean isEvent,
            @Parameter(description = "Topic ID of the report") @RequestParam("topicId") Long topicId,
            @Parameter(description = "The Date of the report")
            @RequestParam(value = "theDate", defaultValue = "2024-09-22T21:30:00") String theDate,
            @Parameter(description = "Space ID of the report") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE), required = false)
            @RequestParam(name = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        try {
            ReportReq report = ReportReq.builder().name(name).description(description).isEvent(isEvent).topicId(topicId).theDate(LocalDateTime.parse(theDate)).spaceId(spaceId).build();
            token = token.substring(7);
            return ResponseEntity.ok(reportService.createReport(report, image, token));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @PutMapping( value = "/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a report", description = "Updates an existing report with the given details")
    @ApiResponse(responseCode = "200", description = "Report updated successfully")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<?> updateReport(
            @PathVariable Long id,
            @Parameter(description = "Name of the report") @RequestParam("name") String name,
            @Parameter(description = "Description of the report") @RequestParam("description") String description,
            @Parameter(description = "isEvent of the report") @RequestParam("isEvent") Boolean isEvent,
            @Parameter(description = "Topic ID of the report") @RequestParam("topicId") Long topicId,
            @Parameter(description = "The Date of the report")
            @RequestParam(value = "theDate", defaultValue = "2024-09-22T21:30:00") String theDate,
            @Parameter(description = "Space ID of the report") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE), required = false)
            @RequestParam(name = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            ReportReq report = ReportReq.builder().name(name).description(description).isEvent(isEvent).topicId(topicId).theDate(LocalDateTime.parse(theDate)).spaceId(spaceId).build();
            ReportRes updatedReport = reportService.updateReport(id, report, image, token);
            return ResponseEntity.ok(updatedReport);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @GetMapping
    @Operation(summary = "Get all reports", description = "Retrieves a list of all reports")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reports")
    public ResponseEntity<List<ReportRes>> getAllReports(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<ReportRes> reports = reportService.getAllReports(token);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a report by ID", description = "Retrieves a report by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the report")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<ReportRes> getReportById(@PathVariable Long id,@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return reportService.getReportById(id, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/summary")
    @Operation(summary = "Get reports summary",
            description = "Retrieves a summary of the total number of reports, including counts for 'APPROVED' and 'REJECTED' statuses, excluding deleted records.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved reports summary", content = @Content(mediaType = "application/json"))
    public ResponseEntity<ReportSummaryRes> getReportSummary(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        ReportSummaryRes reportSummary = reportService.getReportSummary(token);
        return ResponseEntity.ok(reportSummary);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a report", description = "Deletes a report by its ID")
    @ApiResponse(responseCode = "204", description = "Report deleted successfully")
    @ApiResponse(responseCode = "404", description = "Report not found")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            reportService.deleteReport(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
