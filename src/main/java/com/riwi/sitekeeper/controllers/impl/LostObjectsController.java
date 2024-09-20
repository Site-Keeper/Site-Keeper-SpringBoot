package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.services.LostObjectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lost_objects")
@Tag(name = "Lost Objects", description = "Lost Objects Controller")
public class LostObjectsController {

    @Autowired
    LostObjectsService lostObjectService;

    @PostMapping
    @Operation(summary = "Create a new lost object", description = "Creates a new lost object with the given details")
    @ApiResponse(responseCode = "201", description = "Object created successfully")
    public ResponseEntity<LostObjectsRes> createLostObject(@RequestBody LostObjectsReq lostObject,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        LostObjectsRes createLostObject = lostObjectService.createLostObjects(lostObject, token);
        return new ResponseEntity<>(createLostObject, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all lost objects", description = "Retrieves a list of all lost objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of lost objects")
    public ResponseEntity<List<LostObjectsRes>> getAllLostObjects(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        List<LostObjectsRes> lostObject = lostObjectService.getAllLostObjects(token);
        return ResponseEntity.ok(lostObject);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lost object by ID", description = "Retrieves a lost object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the lost object")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> getLostObjectById(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        return lostObjectService.getLostObjectsById(id, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a lost object", description = "Updates an existing lost object with the given details")
    @ApiResponse(responseCode = "200", description = "Lost object updated successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsRes> updateLostObject(@PathVariable Long id, @RequestBody @Valid LostObjectsReq lostObject,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        try {
            LostObjectsRes updateLostObject = lostObjectService.updateLostObjects(id, lostObject, token);
            return ResponseEntity.ok(updateLostObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lost object", description = "Deletes a lost object by its ID")
    @ApiResponse(responseCode = "204", description = "Lost object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<Void> deleteLostObject(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        try {
            lostObjectService.deleteLostObjects(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
