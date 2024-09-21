package com.riwi.sitekeeper.utils;

import com.riwi.sitekeeper.dtos.requests.*;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.services.ObjectService;
import com.riwi.sitekeeper.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransformUtil {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ObjectService objectService;


    public LostObjectsEntity convertToLostObjectsEntity(LostObjectsImgReq lostObjectsReq) {
        // Implementation left empty as requested
        return LostObjectsEntity.builder()
                .name(lostObjectsReq.getName())
                .description(lostObjectsReq.getDescription())
                .image(lostObjectsReq.getImage())
                .spaceId(spaceService.getSpaceById(lostObjectsReq.getSpaceId()).get())
                .build();
    }

    public LostObjectsRes convertToLostObjectsRes(LostObjectsEntity lostObjectsEntity) {
        return LostObjectsRes.builder()
                .id(lostObjectsEntity.getId())
                .name(lostObjectsEntity.getName())
                .description(lostObjectsEntity.getDescription())
                .image(lostObjectsEntity.getImage())
                .spaceId(lostObjectsEntity.getSpaceId().getId())
                .status(lostObjectsEntity.getStatus())
                .build();
    }

    public void updateLostObjectsEntity(LostObjectsEntity existingLostObjects, LostObjectsImgReq updatedLostObjects) {
        existingLostObjects.setName(updatedLostObjects.getName());
        existingLostObjects.setDescription(updatedLostObjects.getDescription());
        existingLostObjects.setImage(updatedLostObjects.getImage());
        existingLostObjects.setSpaceId(spaceService.getSpaceById(updatedLostObjects.getSpaceId()).get());
        existingLostObjects.setStatus(updatedLostObjects.getStatus());
    }

    public ObjectEntity convertToObjectEntity(ObjectImgReq objectReq) {
        return ObjectEntity.builder()
                .name(objectReq.getName())
                .description(objectReq.getDescription())
                .image(objectReq.getImage())
                .spaceId(spaceService.getSpaceById(objectReq.getSpaceId()).get())
                .build();
    }

    public ObjectRes convertToObjectRes(ObjectEntity objectEntity) {
        return ObjectRes.builder()
                .id(objectEntity.getId())
                .name(objectEntity.getName())
                .description(objectEntity.getDescription())
                .image(objectEntity.getImage())
                .spaceId(objectEntity.getSpaceId().getId())
                .build();
    }

    public void updateObjectEntity(ObjectEntity existingObject, ObjectImgReq updatedObject) {
        existingObject.setName(updatedObject.getName());
        existingObject.setDescription(updatedObject.getDescription());
        existingObject.setImage(updatedObject.getImage());
        existingObject.setSpaceId(spaceService.getSpaceById(updatedObject.getSpaceId()).get());
    }


    public ReportEntity convertToReportEntity(ReportReq reportReq, String token) {
        return ReportEntity.builder()
                .name(reportReq.getName())
                .description(reportReq.getDescription())
                .isEvent(reportReq.getIsEvent())
                .image(reportReq.getImage())
                .topicId(reportReq.getTopicId())
                .theDate(reportReq.getTheDate())
                .spaceId(spaceService.getSpaceById(reportReq.getSpaceId()).get())
                .build();
    }

    public ReportRes convertToReportRes(ReportEntity reportEntity) {
        return ReportRes.builder()
                .id(reportEntity.getId())
                .name(reportEntity.getName())
                .description(reportEntity.getDescription())
                .isEvent(reportEntity.getIsEvent())
                .image(reportEntity.getImage())
                .topicId(reportEntity.getTopicId())
                .theDate(reportEntity.getTheDate())
                .spaceId(reportEntity.getSpaceId().getId())
                .build();
    }

    public void updateReportEntity(ReportEntity existingReport, ReportReq updatedReport) {
        existingReport.setName(updatedReport.getName());
        existingReport.setDescription(updatedReport.getDescription());
        existingReport.setIsEvent(updatedReport.getIsEvent());
        existingReport.setImage(updatedReport.getImage());
        existingReport.setTopicId(updatedReport.getTopicId());
        existingReport.setTheDate(updatedReport.getTheDate());
        existingReport.setSpaceId(spaceService.getSpaceById(updatedReport.getSpaceId()).get());
    }



    public SpaceEntity convertToSpaceEntity(SpaceImgReq spaceReq) {
        return SpaceEntity.builder()
                .name(spaceReq.getName())
                .location(spaceReq.getLocation())
                .description(spaceReq.getDescription())
                .image(spaceReq.getImage())
                .build();
    }

    public SpaceRes convertToSpaceRes(SpaceEntity spaceEntity) {
        return SpaceRes.builder()
                .id(spaceEntity.getId())
                .name(spaceEntity.getName())
                .location(spaceEntity.getLocation())
                .description(spaceEntity.getDescription())
                .image(spaceEntity.getImage())
                .objects(Optional.ofNullable(spaceEntity.getObjects())
                        .map(objects -> objects.stream()
                                .map(this::convertToObjectRes)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .build();
    }

    public void updateSpaceEntity(SpaceEntity existingSpace, SpaceImgReq updatedSpace) {
        existingSpace.setName(updatedSpace.getName());
        existingSpace.setLocation(updatedSpace.getLocation());
        existingSpace.setDescription(updatedSpace.getDescription());
        existingSpace.setImage(updatedSpace.getImage());
    }
}
