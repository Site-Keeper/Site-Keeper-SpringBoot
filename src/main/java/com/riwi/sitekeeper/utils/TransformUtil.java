package com.riwi.sitekeeper.utils;

import com.riwi.sitekeeper.dtos.requests.*;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entities.LostObjectsEntity;
import com.riwi.sitekeeper.entities.ObjectEntity;
import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.entities.SpaceEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import com.riwi.sitekeeper.enums.ReportStatus;
import com.riwi.sitekeeper.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransformUtil {

    @Autowired
    private SpaceService spaceService;


    public LostObjectsEntity convertToLostObjectsEntity(LostObjectsImgReq lostObjectsReq) {
        // Implementation left empty as requested
        return LostObjectsEntity.builder()
                .name(lostObjectsReq.getName())
                .description(lostObjectsReq.getDescription())
                .image(lostObjectsReq.getImage())
                .spaceId(spaceService.getSpaceById(lostObjectsReq.getSpaceId()).get())
                .status(LostObjectsStatus.PERDIDO)
                .build();
    }

    public LostObjectsRes convertToLostObjectsRes(LostObjectsEntity lostObjectsEntity) {
        return LostObjectsRes.builder()
                .id(lostObjectsEntity.getId())
                .name(lostObjectsEntity.getName())
                .description(lostObjectsEntity.getDescription())
                .image(lostObjectsEntity.getImage())
                .spaceId(lostObjectsEntity.getSpaceId().getId())
                .location(lostObjectsEntity.getSpaceId().getLocation())
                .status(lostObjectsEntity.getStatus())
                .build();
    }

    public void updateLostObjectsEntity(LostObjectsEntity existingLostObjects, LostObjectsImgReq updatedLostObjects) {
        existingLostObjects.setName(updatedLostObjects.getName());
        existingLostObjects.setDescription(updatedLostObjects.getDescription());
        existingLostObjects.setImage(updatedLostObjects.getImage());
        existingLostObjects.setSpaceId(spaceService.getSpaceById(updatedLostObjects.getSpaceId()).get());
    }

    public ObjectEntity convertToObjectEntity(ObjectReq objectReq) {
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

    public void updateObjectEntity(ObjectEntity existingObject, ObjectReq updatedObject) {
        existingObject.setName(updatedObject.getName());
        existingObject.setDescription(updatedObject.getDescription());
        existingObject.setImage(updatedObject.getImage());
    }


    public ReportEntity convertToReportEntity(ReportImgReq reportImgReq) {
        System.out.println(reportImgReq);
        return ReportEntity.builder()
                .name(reportImgReq.getName())
                .description(reportImgReq.getDescription())
                .isEvent(reportImgReq.getIsEvent())
                .image(reportImgReq.getImage())
                .topicId(reportImgReq.getTopicId())
                .theDate(reportImgReq.getTheDate())
                .spaceId(spaceService.getSpaceById(reportImgReq.getSpaceId()).get())
                .status(ReportStatus.PENDING)
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
                .status(reportEntity.getStatus())
                .spaceId(reportEntity.getSpaceId().getId())
                .build();
    }

    public void updateReportEntity(ReportEntity existingReport, ReportImgReq updatedReport) {
        existingReport.setName(updatedReport.getName());
        existingReport.setDescription(updatedReport.getDescription());
        existingReport.setIsEvent(updatedReport.getIsEvent());
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
    }
}
