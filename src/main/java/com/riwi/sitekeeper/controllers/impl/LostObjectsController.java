package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.dtos.responses.LostObjectsSummaryRes;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import com.riwi.sitekeeper.services.LostObjectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/lost_objects")
@Tag(name = "Lost Objects", description = "Lost Objects Controller")
public class LostObjectsController {

    @Autowired
    LostObjectsService lostObjectsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new lost object", description = "Creates a new lost object with the given details")
    @ApiResponse(responseCode = "201", description = "Lost object created successfully")
    public ResponseEntity<LostObjectsRes> createLostObject(
            @Parameter(description = "Name of the lost object") @RequestParam("name") String name,
            @Parameter(description = "Description of the lost object") @RequestParam("description") String description,
            @Parameter(description = "Space ID") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("image") MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            LostObjectsReq lostObjectReq = new LostObjectsReq(name, description, spaceId);
            LostObjectsRes createdLostObject = lostObjectsService.createLostObjects(lostObjectReq, image, token);
            return new ResponseEntity<>(createdLostObject, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a lost object", description = "Updates an existing lost object with the given details")
    @ApiResponse(responseCode = "200", description = "Lost object updated successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> updateLostObject(
            @PathVariable Long id,
            @Parameter(description = "Name of the lost object") @RequestParam("name") String name,
            @Parameter(description = "Description of the lost object") @RequestParam("description") String description,
            @Parameter(description = "Space ID") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image file (optional)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            LostObjectsReq lostObjectReq = new LostObjectsReq(name, description, spaceId);
            LostObjectsRes updatedLostObject = lostObjectsService.updateLostObjects(id, lostObjectReq, image, token);
            return ResponseEntity.ok(updatedLostObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Get all lost objects", description = "Retrieves a list of all lost objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of lost objects")
    public ResponseEntity<List<LostObjectsRes>> getAllLostObjects(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<LostObjectsRes> lostObject = lostObjectsService.getAllLostObjects(token);
        return ResponseEntity.ok(lostObject);
    }

    @GetMapping("/recently-claimed")
    @Operation(summary = "Get recently claimed lost objects",
            description = "Retrieves lost objects with status 'RECLAMADO' and updated within the last 2 days")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of recently claimed lost objects")
    public ResponseEntity<List<LostObjectsRes>> getRecentlyClaimedObjects(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<LostObjectsRes> recentlyClaimedObjects = lostObjectsService.getRecentlyClaimedObjects(token);
        return ResponseEntity.ok(recentlyClaimedObjects);
    }

    @GetMapping("/summary")
    @Operation(summary = "Get lost objects summary",
            description = "Retrieves a summary of the total number of lost objects, including counts for 'PERDIDO' and 'RECLAMADO' statuses, excluding deleted records.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved lost objects summary", content = @Content(mediaType = "application/json"))
    public ResponseEntity<LostObjectsSummaryRes> getLostObjectSummary(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        LostObjectsSummaryRes lostObjectSummary = lostObjectsService.getLostObjectsSummary(token);
        return ResponseEntity.ok(lostObjectSummary);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lost object by ID", description = "Retrieves a lost object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the lost object")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> getLostObjectById(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return lostObjectsService.getLostObjectsById(id, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lost object", description = "Deletes a lost object by its ID")
    @ApiResponse(responseCode = "204", description = "Lost object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<Void> deleteLostObject(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            lostObjectsService.deleteLostObjects(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Lost object status update", description = "Updates the status of a lost object")
    @ApiResponse(responseCode = "200", description = "Lost object's status updated successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> updateStatus(@PathVariable Long id,@Parameter(hidden = true)@RequestHeader("Authorization")String token, @RequestParam LostObjectsStatus status){
        token = token.substring(7);
        LostObjectsRes updatedStatus = lostObjectsService.updateStatus(id, status, token);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }
}
