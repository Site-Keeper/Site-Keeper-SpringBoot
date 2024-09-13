package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @PostMapping
    @Operation(summary = "Create a new space", description = "Creates a new Space")
    @ApiResponse(responseCode = "201", description = "Space successfully created")
    public ResponseEntity<SpaceEntity> CreateSpace (@RequestBody SpaceEntity space){
        return ResponseEntity.ok(spaceService.create(space));
    }

    @PutMapping
    @Operation(summary = "Update an Space", description = "Updates an space")
    @ApiResponse(responseCode = "200", description = "Space successfully Updated")
    @ApiResponse(responseCode = "404", description = "Unable to update Space")
    public ResponseEntity<SpaceEntity> UpdateSpace (@RequestBody SpaceEntity space){
        return ResponseEntity.ok(spaceService.update(space));
    }

    @GetMapping
    @Operation(summary = "List all spaces", description = "Lists all Spaces")
    @ApiResponse(responseCode = "200", description = "Spaces successfully gathered")
    public ResponseEntity<List<SpaceEntity>> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Space by Id", description = "Gets an Space based on the Id")
    @ApiResponse(responseCode = "200", description = "Space successfully found")
    @ApiResponse(responseCode = "404", description = "Unable to find Space with the Id provided")
    public ResponseEntity<SpaceEntity> getSpaceById(@PathVariable Long id){
        Optional<SpaceEntity> space = spaceService.getById(id);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 }
