package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spaces")
@Tag(name = "Spaces", description = "Spaces Controller")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new space", description = "Creates a new Space")
    @ApiResponse(responseCode = "201", description = "Space successfully created")
    public ResponseEntity<?> createSpace(
            @Parameter(description = "Name of the space") @RequestParam("name") String name,
            @Parameter(description = "Location of the space") @RequestParam("location") String location,
            @Parameter(description = "Description of the space") @RequestParam("description") String description,
            @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("image") MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        try {
            SpaceReq space = new SpaceReq(name, location, description);
            token = token.substring(7);
            return ResponseEntity.ok(spaceService.createSpace(space, image, token));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a Space", description = "Updates a space")
    @ApiResponse(responseCode = "200", description = "Space successfully Updated")
    @ApiResponse(responseCode = "404", description = "Unable to update Space")
    public ResponseEntity<?> updateSpace(
            @PathVariable Long id,
            @Parameter(description = "Name of the space") @RequestParam("name") String name,
            @Parameter(description = "Location of the space") @RequestParam("location") String location,
            @Parameter(description = "Description of the space") @RequestParam("description") String description,
            @Parameter(description = "Image file (optional)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            SpaceReq spaceReq = new SpaceReq(name, location, description);
            SpaceRes updatedSpace = spaceService.updateSpace(id, spaceReq, image, token);
            return ResponseEntity.ok(updatedSpace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @GetMapping
    @Operation(summary = "List all spaces", description = "Lists all Spaces")
    @ApiResponse(responseCode = "200", description = "Spaces successfully gathered")
    public ResponseEntity<List<SpaceRes>> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Space by Id", description = "Gets an Space based on the Id")
    @ApiResponse(responseCode = "200", description = "Space successfully found")
    @ApiResponse(responseCode = "404", description = "Unable to find Space with the Id provided")
    public ResponseEntity<SpaceRes> getSpaceById(@PathVariable Long id, @Parameter(hidden = true) @RequestHeader("Authorization") String token){
        token = token.substring(7);
        SpaceRes space = spaceService.getSpaceById(id, token);
        return new ResponseEntity<>(space, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Find Space by Name", description = "Gets an Space based on the Name")
    @ApiResponse(responseCode = "200", description = "Space successfully found")
    @ApiResponse(responseCode = "404", description = "Unable to find Space with the Name provided")
    public ResponseEntity<SpaceRes> getSpaceByName(@PathVariable String name, @Parameter(hidden = true) @RequestHeader("Authorization") String token){
        token = token.substring(7);
        Optional<SpaceRes> space = spaceService.getSpaceByName(name, token);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search spaces by name", description = "Searches for spaces containing the given name string")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of spaces")
    public ResponseEntity<List<SpaceRes>> searchSpaces(
            @Parameter(description = "Name to search for") @RequestParam String name,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        List<SpaceRes> spaces = spaceService.getSpacesByName(name, token);
        return ResponseEntity.ok(spaces);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a space", description = "Deletes a space by its ID")
    @ApiResponse(responseCode = "204", description = "Space deleted successfully")
    @ApiResponse(responseCode = "404", description = "Space not found")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            spaceService.deleteSpace(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}