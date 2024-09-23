package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ObjectImgReq;
import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.requests.SpaceImgReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.exceptions.reports.NotFoundException;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired
    private Cloudinary cloudinary;

    private final TransformUtil transformUtil = new TransformUtil();

    public List<ObjectRes> getAllObjects(String token) {
        List<ObjectEntity> objects = objectRepository.findAllByIsDeletedFalse();
        List<ObjectRes> objectResList = new ArrayList<>();
        for (ObjectEntity object : objects) {
            objectResList.add(transformUtil.convertToObjectRes(object));
        }
        return objectResList;
    }

    public ObjectRes getObjectById(Long id, String token) {
        ObjectEntity object = objectRepository.findById(id).orElseThrow(()-> new NotFoundException("Object could not be found by id"));
        return transformUtil.convertToObjectRes(object);
    }

    public Optional<ObjectRes> getObjectByName(String name, String token) {
        Optional<ObjectEntity> objectOptional = objectRepository.findByName(name);
        return objectOptional.map(transformUtil::convertToObjectRes);
    }

    public ObjectRes createObject(ObjectReq object, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("objects", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        ObjectImgReq imgReq = new ObjectImgReq(
                object.getName(),
                object.getDescription(),
                imageUrl,
                object.getSpaceId()
        );

        ObjectEntity newObject = transformUtil.convertToObjectEntity(imgReq);
        newObject.setImage(imageUrl);
        newObject.setCreatedBy(user.getId());
        newObject.setUpdatedBy(user.getId());

        ObjectEntity savedObject = objectRepository.save(newObject);
        return transformUtil.convertToObjectRes(savedObject);
    }

    public ObjectRes updateObject(Long id, ObjectReq updatedObject, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("objects", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<ObjectEntity> existingObjectOptional = objectRepository.findById(id);

        if (existingObjectOptional.isPresent()) {
            ObjectEntity existingObject = existingObjectOptional.get();

            existingObject.setName(updatedObject.getName());
            existingObject.setDescription(updatedObject.getDescription());
            existingObject.setUpdatedBy(user.getId());

            // Update image only if a new one is provided
            if (image != null && !image.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                existingObject.setImage(imageUrl);
            }

            ObjectEntity savedObject = objectRepository.save(existingObject);
            return transformUtil.convertToObjectRes(savedObject);
        } else {
            throw new RuntimeException("Object not found with id: " + id);
        }
    }

    public void deleteObject(Long id, String token) {
        objectRepository.softDeleteById(id);
    }
}