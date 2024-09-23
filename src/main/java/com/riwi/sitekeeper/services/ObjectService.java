package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.entities.ObjectEntity;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired
    private Cloudinary cloudinary;

    private final TransformUtil transformUtil;

    public ObjectService(TransformUtil transformUtil) {
        this.transformUtil = transformUtil;
    }

    public List<ObjectRes> getAllObjects(String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<ObjectEntity> objects = objectRepository.findAllByIsDeletedFalse();
        List<ObjectRes> objectResList = new ArrayList<>();
        for (ObjectEntity object : objects) {
            objectResList.add(transformUtil.convertToObjectRes(object));
        }
        return objectResList;
    }

    public Optional<ObjectRes> getObjectById(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<ObjectEntity> objectOptional = objectRepository.findById(id);
        return objectOptional.map(transformUtil::convertToObjectRes);
    }

    public Optional<ObjectRes> getObjectByName(String name, String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<ObjectEntity> objectOptional = objectRepository.findByName(name);
        return objectOptional.map(transformUtil::convertToObjectRes);
    }

    public ObjectRes createObject(ObjectReq object, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("objects", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        System.out.println("RECEIVED OBJECT: " + object);
        ObjectEntity newObject = transformUtil.convertToObjectEntity(object);
        System.out.println("CONVERTED OBJECT: " + newObject);
        newObject.setCreatedBy(user.getId());
        newObject.setUpdatedBy(user.getId());

        ObjectEntity savedObject = objectRepository.save(newObject);
        return transformUtil.convertToObjectRes(savedObject);
    }

    public ObjectRes updateObject(Long id, ObjectReq updatedObject, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("objects", "can_update");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<ObjectEntity> existingObjectOptional = objectRepository.findById(id);

        if (existingObjectOptional.isPresent()) {
            ObjectEntity existingObject = existingObjectOptional.get();

            existingObject.setName(updatedObject.getName());
            existingObject.setDescription(updatedObject.getDescription());
            existingObject.setImage(updatedObject.getImage());
            existingObject.setUpdatedBy(user.getId());

            ObjectEntity savedObject = objectRepository.save(existingObject);
            return transformUtil.convertToObjectRes(savedObject);
        } else {
            throw new RuntimeException("Object not found with id: " + id);
        }
    }

    public void deleteObject(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_delete");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        objectRepository.softDeleteById(id);
    }
}