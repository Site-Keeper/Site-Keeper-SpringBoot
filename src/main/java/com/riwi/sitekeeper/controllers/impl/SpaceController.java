package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<SpaceRes> CreateSpace (@RequestBody SpaceReq space){
        return ResponseEntity.ok(spaceService.createSpace(space));
    }

    @PutMapping
    @Operation(summary = "Update an Space", description = "Updates an space")
    @ApiResponse(responseCode = "200", description = "Space successfully Updated")
    @ApiResponse(responseCode = "404", description = "Unable to update Space")
    public ResponseEntity<SpaceRes> UpdateSpace (@PathVariable Long id, @RequestBody @Valid SpaceReq space){
        try {
            SpaceRes updatedSpace = spaceService.updateSpace(id, space);
            return ResponseEntity.ok(updatedSpace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<SpaceRes> getSpaceById(@PathVariable Long id){
        Optional<SpaceRes> space = spaceService.getSpaceById(id);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
