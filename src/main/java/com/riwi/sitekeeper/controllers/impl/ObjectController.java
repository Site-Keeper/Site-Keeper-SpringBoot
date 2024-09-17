package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.services.ObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objects")
@Tag(name = "Objects", description = "Objects Controller")
public class ObjectController {

    @Autowired
    ObjectService objectService;

    @PostMapping
    @Operation(summary = "Create a new object", description = "Creates a new object with the given details")
    @ApiResponse(responseCode = "201", description = "Object created successfully")
    public ResponseEntity<ObjectRes> createObject(@RequestBody @Valid ObjectReq object) {
        ObjectRes createObject = objectService.createObject(object);
        return new ResponseEntity<>(createObject, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Retrieves a list of all objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of objects")
    public ResponseEntity<List<ObjectRes>> getAllObjects() {
        List<ObjectRes> objects = objectService.getAllObjects();
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a object by ID", description = "Retrieves a object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the object")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> getObjectById(@PathVariable Long id) {
        return objectService.getObjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get a object by name", description = "Retrieves a object by its name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the object")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> getObjectByName(@PathVariable String name) {
        return objectService.getObjectByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a object", description = "Updates an existing object with the given details")
    @ApiResponse(responseCode = "200", description = "Object updated successfully")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> updateObject(@PathVariable Long id, @RequestBody @Valid ObjectReq object) {
        try {
            ObjectRes updateObject = objectService.updateObject(id, object);
            return ResponseEntity.ok(updateObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a object", description = "Deletes a object by its ID")
    @ApiResponse(responseCode = "204", description = "Object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<Void> deleteObject(@PathVariable Long id) {
        try {
            objectService.deleteObject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
