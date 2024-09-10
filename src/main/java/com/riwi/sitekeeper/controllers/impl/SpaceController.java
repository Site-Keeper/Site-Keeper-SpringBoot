package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.SpaceService;
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
    public ResponseEntity<SpaceEntity> CreateSpace (@RequestBody SpaceEntity space){
        return ResponseEntity.ok(spaceService.create(space));
    }

    @PutMapping
    public ResponseEntity<SpaceEntity> UpdateSpace (@RequestBody SpaceEntity space){
        return ResponseEntity.ok(spaceService.update(space));
    }

    @GetMapping
    public ResponseEntity<List<SpaceEntity>> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceEntity> getSpaceById(@PathVariable Long id){
        Optional<SpaceEntity> space = spaceService.getById(id);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 }
