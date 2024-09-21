package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import com.riwi.sitekeeper.services.LostObjectsService;
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
import java.util.List;

@RestController
@RequestMapping("/api/lost_objects")
@Tag(name = "Lost Objects", description = "Lost Objects Controller")
public class LostObjectsController {

    @Autowired
    LostObjectsService lostObjectService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new lost object", description = "Creates a new lost object with the given details")
    @ApiResponse(responseCode = "201", description = "Lost object created successfully")
    public ResponseEntity<LostObjectsRes> createLostObject(
            @Parameter(description = "Name of the lost object") @RequestParam("name") String name,
            @Parameter(description = "Description of the lost object") @RequestParam("description") String description,
            @Parameter(description = "Space ID") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Status of the lost object") @RequestParam("status") LostObjectsStatus status,
            @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("image") MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            LostObjectsReq lostObjectReq = new LostObjectsReq(name, description, spaceId, status);
            LostObjectsRes createdLostObject = lostObjectService.createLostObjects(lostObjectReq, image, token);
            return new ResponseEntity<>(createdLostObject, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Get all lost objects", description = "Retrieves a list of all lost objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of lost objects")
    public ResponseEntity<List<LostObjectsRes>> getAllLostObjects(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<LostObjectsRes> lostObject = lostObjectService.getAllLostObjects(token);
        return ResponseEntity.ok(lostObject);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lost object by ID", description = "Retrieves a lost object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the lost object")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> getLostObjectById(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return lostObjectService.getLostObjectsById(id, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
            @Parameter(description = "Status of the lost object") @RequestParam("status") LostObjectsStatus status,
            @Parameter(description = "Image file (optional)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            LostObjectsReq lostObjectReq = new LostObjectsReq(name, description, spaceId, status);
            LostObjectsRes updatedLostObject = lostObjectService.updateLostObjects(id, lostObjectReq, image, token);
            return ResponseEntity.ok(updatedLostObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lost object", description = "Deletes a lost object by its ID")
    @ApiResponse(responseCode = "204", description = "Lost object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<Void> deleteLostObject(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            lostObjectService.deleteLostObjects(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
