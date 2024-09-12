package com.riwi.sitekeeper.controllers.impl;

import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.services.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objects")
public class ObjectController {

    @Autowired
    ObjectService objectService;

    @PostMapping
    public ResponseEntity<ObjectEntity> createObject (@RequestBody ObjectEntity object) {

        return ResponseEntity.ok(objectService.createObject(object));

    }

    @GetMapping
    public ResponseEntity<ObjectEntity> updateObject (@RequestBody ObjectEntity object) {

        return  ResponseEntity.ok(objectService.update(object));

    }

    @GetMapping
    public ResponseEntity<List<ObjectEntity>> getAllObjects() {

        return  ResponseEntity.ok(objectService.getAllObjects());

    }

}
