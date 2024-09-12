package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.services.LostObjectService;
import com.riwi.sitekeeper.services.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lost_objects")
public class LostObjectsController {

    @Autowired
    LostObjectService lostObjectService;

    @PostMapping
    public ResponseEntity<LostObjectsEntity> createObject (@RequestBody LostObjectsEntity lostObjects) {

        return ResponseEntity.ok(lostObjectService.createLostObject(lostObjects));

    }

    @GetMapping
    public ResponseEntity<LostObjectsEntity> updateObject (@RequestBody LostObjectsEntity lostObjects) {

        return  ResponseEntity.ok(lostObjectService.update(lostObjects));

    }

    @GetMapping
    public ResponseEntity<List<LostObjectsEntity>> getAllObjects() {

        return  ResponseEntity.ok(lostObjectService.getAllLostObjects());

    }

}
