package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.services.ObjectService;
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

import javax.swing.text.html.Option;
import java.io.IOException;
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
    public ResponseEntity<ObjectRes> createObject(
            @Parameter(description = "Name of the object") @RequestParam("name") String name,
            @Parameter(description = "Description of the object") @RequestParam("description") String description,
            @Parameter(description = "Space ID") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image as a base64 string") @RequestParam("image") String image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        token = token.substring(7); // Assuming "Bearer " prefix
        try {
            ObjectReq objectReq = new ObjectReq(name, description, image, spaceId);
            objectReq.setImage(image); // Assuming you have a setter for the image in ObjectReq
            ObjectRes createdObject = objectService.createObject(objectReq, token);
            return new ResponseEntity<>(createdObject, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Retrieves a list of all objects")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of objects")
    public ResponseEntity<List<ObjectRes>> getAllObjects(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<ObjectRes> objects = objectService.getAllObjects(token);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a object by ID", description = "Retrieves a object by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the object")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> getObjectById(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return objectService.getObjectById(id, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get a object by name", description = "Retrieves a object by its name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the object")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> getObjectByName(@PathVariable String name,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return objectService.getObjectByName(name, token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an object", description = "Updates an existing object with the given details")
    @ApiResponse(responseCode = "200", description = "Object updated successfully")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<ObjectRes> updateObject(
            @PathVariable Long id,
            @Parameter(description = "Name of the object") @RequestParam("name") String name,
            @Parameter(description = "Description of the object") @RequestParam("description") String description,
            @Parameter(description = "Space ID") @RequestParam("spaceId") Long spaceId,
            @Parameter(description = "Image as an icon string")
            @RequestParam(value = "image", required = false) String image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        try {
            ObjectReq objectReq = new ObjectReq(name, description,image , spaceId);
            objectReq.setImage(image);
            ObjectRes updatedObject = objectService.updateObject(id, objectReq, token);
            return ResponseEntity.ok(updatedObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a object", description = "Deletes a object by its ID")
    @ApiResponse(responseCode = "204", description = "Object deleted successfully")
    @ApiResponse(responseCode = "404", description = "Object not found")
    public ResponseEntity<Void> deleteObject(@PathVariable Long id,@Parameter(hidden = true)  @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            objectService.deleteObject(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
