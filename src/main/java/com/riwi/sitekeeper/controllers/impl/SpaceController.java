package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/space")
@Tag(name = "Spaces", description = "Spaces Controller")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @PostMapping
    @Operation(summary = "Create a new space", description = "Creates a new Space")
    @ApiResponse(responseCode = "201", description = "Space successfully created")
    public ResponseEntity<SpaceRes> CreateSpace (@RequestBody SpaceReq space,@Parameter(hidden = true) @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(spaceService.createSpace(space, token));
    }

    @PutMapping
    @Operation(summary = "Update an Space", description = "Updates an space")
    @ApiResponse(responseCode = "200", description = "Space successfully Updated")
    @ApiResponse(responseCode = "404", description = "Unable to update Space")
    public ResponseEntity<SpaceRes> UpdateSpace (@PathVariable Long id, @RequestBody @Valid SpaceReq space,@Parameter(hidden = true) @RequestHeader("Authorization") String token){
        try {
            SpaceRes updatedSpace = spaceService.updateSpace(id, space, token);
            return ResponseEntity.ok(updatedSpace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "List all spaces", description = "Lists all Spaces")
    @ApiResponse(responseCode = "200", description = "Spaces successfully gathered")
    public ResponseEntity<List<SpaceRes>> getAllSpaces(@Parameter(hidden = true) @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(spaceService.getAllSpaces(token));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Space by Id", description = "Gets an Space based on the Id")
    @ApiResponse(responseCode = "200", description = "Space successfully found")
    @ApiResponse(responseCode = "404", description = "Unable to find Space with the Id provided")
    public ResponseEntity<SpaceRes> getSpaceById(@PathVariable Long id, @Parameter(hidden = true) @RequestHeader("Authorization") String token){
        Optional<SpaceRes> space = spaceService.getSpaceById(id, token);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Find Space by Name", description = "Gets an Space based on the Name")
    @ApiResponse(responseCode = "200", description = "Space successfully found")
    @ApiResponse(responseCode = "404", description = "Unable to find Space with the Name provided")
    public ResponseEntity<SpaceRes> getSpaceByName(@PathVariable String name, @Parameter(hidden = true) @RequestHeader("Authorization") String token){
        Optional<SpaceRes> space = spaceService.getSpaceByName(name, token);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a space", description = "Deletes a space by its ID")
    @ApiResponse(responseCode = "204", description = "Space deleted successfully")
    @ApiResponse(responseCode = "404", description = "Space not found")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        try {
            spaceService.deleteSpace(id, token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
