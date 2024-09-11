package com.riwi.sitekeeper.controllers;

import com.riwi.sitekeeper.dtos.requests.TopicReq;
import com.riwi.sitekeeper.dtos.responses.TopicRes;
import com.riwi.sitekeeper.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@Tag(name = "Topics", description = "Topics Controller")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    @Operation(summary = "Create a new topic", description = "Creates a new topic with the given details")
    @ApiResponse(responseCode = "201", description = "Topic created successfully")
    public ResponseEntity<TopicRes> createTopic(@RequestBody @Valid TopicReq topic) {
        TopicRes createdTopic = topicService.createTopic(topic);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all topics", description = "Retrieves a list of all topics")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of topics")
    public ResponseEntity<List<TopicRes>> getAllTopics() {
        List<TopicRes> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a topic by ID", description = "Retrieves a topic by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the topic")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    public ResponseEntity<TopicRes> getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a topic", description = "Updates an existing topic with the given details")
    @ApiResponse(responseCode = "200", description = "Topic updated successfully")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    public ResponseEntity<TopicRes> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicReq topic) {
        try {
            TopicRes updatedTopic = topicService.updateTopic(id, topic);
            return ResponseEntity.ok(updatedTopic);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a topic", description = "Deletes a topic by its ID")
    @ApiResponse(responseCode = "204", description = "Topic deleted successfully")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        try {
            topicService.deleteTopic(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
