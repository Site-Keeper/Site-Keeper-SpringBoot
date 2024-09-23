package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.responses.ObjectRes;
import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.exceptions.General.NotFoundException;
import com.riwi.sitekeeper.exceptions.General.InvalidFileException;
import com.riwi.sitekeeper.exceptions.General.UnauthorizedActionException;
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

    public ObjectRes getObjectById(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
  
        ObjectEntity object = objectRepository.findById(id).orElseThrow(()-> new NotFoundException("Object could not be found by id"));
        return transformUtil.convertToObjectRes(object);

    }

    public Optional<ObjectRes> getObjectByName(String name, String token) {
        ValidationReq validationReq = new ValidationReq("objects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        ObjectEntity objectOptional = objectRepository.findByName(name).orElseThrow(()-> new NotFoundException("Object could not be found by name"));

        return Optional.of(transformUtil.convertToObjectRes(objectOptional));
    }

    public ObjectRes createObject(ObjectReq object, MultipartFile image, String token) throws IOException {
        try {
            if (image == null || image.isEmpty()) {
                throw new InvalidFileException("Image File is Required");
            }
            String fileType = image.getContentType();
            if (fileType == null || !fileType.startsWith("image/")) {
                throw new InvalidFileException("Invalid file type. Only image files are supported.");
            }
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
        } catch (UnauthorizedActionException e) {
            throw new UnauthorizedActionException("User does not have permission to create Objects");
        }
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