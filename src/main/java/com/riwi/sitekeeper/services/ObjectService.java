package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private SpaceService spaceService;

    public List<ObjectRes> getAllObjects(String token) {
        List<ObjectEntity> objects = objectRepository.findAllByIsDeletedFalse();
        List<ObjectRes> objectResList = new ArrayList<>();
        for (ObjectEntity object : objects) {
            objectResList.add(convertToObjectRes(object));
        }
        return objectResList;
    }

    public Optional<ObjectRes> getObjectById(Long id, String token) {
        Optional<ObjectEntity> objectOptional = objectRepository.findById(id);
        return objectOptional.map(this::convertToObjectRes);
    }

    public Optional<ObjectRes> getObjectByName(String name, String token) {
        Optional<ObjectEntity> objectOptional = objectRepository.findByName(name);
        return objectOptional.map(this::convertToObjectRes);
    }

    public ObjectRes createObject(ObjectReq object, String token) {
        ObjectEntity newObject = convertToObjectEntity(object);
        ObjectEntity savedObject = objectRepository.save(newObject);
        return convertToObjectRes(savedObject);
    }

    public ObjectRes updateObject(Long id, ObjectReq updatedObject, String token) {
        Optional<ObjectEntity> existingObjectOptional = objectRepository.findById(id);

        if (existingObjectOptional.isPresent()) {
            ObjectEntity existingObject = existingObjectOptional.get();
            updateObjectEntity(existingObject, updatedObject);
            ObjectEntity savedObject = objectRepository.save(existingObject);
            return convertToObjectRes(savedObject);
        } else {
            throw new RuntimeException("Object not found with id: " + id);
        }
    }

    public void deleteObject(Long id, String token) {
        objectRepository.softDeleteById(id);
    }

    private ObjectEntity convertToObjectEntity(ObjectReq objectReq) {
        return ObjectEntity.builder()
                .name(objectReq.getName())
                .description(objectReq.getDescription())
                .image(objectReq.getImage())
                .spaceId(spaceService.getSpaceById(objectReq.getSpaceId()).get())
                .build();
    }

    private ObjectRes convertToObjectRes(ObjectEntity objectEntity) {
        return ObjectRes.builder()
                .id(objectEntity.getId())
                .name(objectEntity.getName())
                .description(objectEntity.getDescription())
                .image(objectEntity.getImage())
                .spaceId(objectEntity.getSpaceId().getId())
                .build();
    }

    private void updateObjectEntity(ObjectEntity existingObject, ObjectReq updatedObject) {
        existingObject.setName(updatedObject.getName());
        existingObject.setDescription(updatedObject.getDescription());
        existingObject.setImage(updatedObject.getImage());
        existingObject.setSpaceId(spaceService.getSpaceById(updatedObject.getSpaceId()).get());
    }
}