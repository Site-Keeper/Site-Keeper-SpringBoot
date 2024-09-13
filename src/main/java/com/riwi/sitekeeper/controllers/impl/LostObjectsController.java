package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.services.LostObjectService;
import com.riwi.sitekeeper.services.ObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lost_objects")
public class LostObjectsController {

    @Autowired
    LostObjectService lostObjectService;

    @PostMapping
    @Operation(summary = "Create a new lost object", description = "Creates a new lost object with the given details")
    @ApiResponse(responseCode = "201", description = "Object created successfully")
    public ResponseEntity<LostObjectsEntity> createLostObject(@RequestBody @Valid LostObjectsEntity lostObject) {
        LostObjectsEntity createLostObject = lostObjectService.createLostObject(lostObject);
        return new ResponseEntity<>(createLostObject, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all lost objects", description = "Retrieves a list of all lost objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of lost objects")
    public ResponseEntity<List<LostObjectsEntity>> getAllLostObjects() {
        List<LostObjectsEntity> lostObject = lostObjectService.getAllLostObjects();
        return ResponseEntity.ok(lostObject);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lost object by ID", description = "Retrieves a lost object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the lost object")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsEntity> getLostObjectById(@PathVariable Long id) {
        return lostObjectService.getLostObjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a lost object", description = "Updates an existing lost object with the given details")
    @ApiResponse(responseCode = "200", description = "Lost object updated successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<LostObjectsEntity> updateLostObject(@PathVariable Long id, @RequestBody @Valid LostObjectsEntity lostObject) {
        try {
            LostObjectsEntity updateLostObject = lostObjectService.update(lostObject);
            return ResponseEntity.ok(updateLostObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lost object", description = "Deletes a lost object by its ID")
    @ApiResponse(responseCode = "204", description = "Lost object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Lost object not found")
    public ResponseEntity<Void> deleteLostObject(@PathVariable Long id) {
        try {
            lostObjectService.deleteLostObject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
