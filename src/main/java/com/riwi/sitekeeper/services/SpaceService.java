package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.SpaceImgReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired
    private Cloudinary cloudinary;

    private final TransformUtil transformUtil = new TransformUtil();

    public List<SpaceRes> getAllSpaces(String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<SpaceEntity> spaces = spaceRepository.findAllByIsDeletedFalse();
        List<SpaceRes> spaceResList = new ArrayList<>();
        for (SpaceEntity space : spaces) {
            spaceResList.add(transformUtil.convertToSpaceRes(space));
        }
        return spaceResList;
    }

    public Optional<SpaceRes> getSpaceById(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<SpaceEntity> spaceOptional = spaceRepository.findById(id);
        return spaceOptional.map(transformUtil::convertToSpaceRes);
    }

    public Optional<SpaceEntity> getSpaceById(Long id) {
        return spaceRepository.findById(id);
    }

    public Optional<SpaceRes> getSpaceByName(String name, String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<SpaceEntity> spaceOptional = spaceRepository.findByName(name);
        return spaceOptional.map(transformUtil::convertToSpaceRes);
    }

    public List<SpaceRes> getSpacesByName(String name, String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<SpaceEntity> spaces = spaceRepository.findByNameContainingIgnoreCase(name);
        return spaces.stream()
                .map(transformUtil::convertToSpaceRes)
                .collect(Collectors.toList());
    }

    public SpaceRes createSpace(SpaceReq space, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("spaces", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        SpaceImgReq imgReq = new SpaceImgReq(
                space.getName(),
                space.getLocation(),
                space.getDescription(),
                imageUrl
        );

        SpaceEntity newSpace = transformUtil.convertToSpaceEntity(imgReq);
        newSpace.setImage(imageUrl);
        newSpace.setCreatedBy(user.getId());
        newSpace.setUpdatedBy(user.getId());

        SpaceEntity savedSpace = spaceRepository.save(newSpace);
        return transformUtil.convertToSpaceRes(savedSpace);
    }

    public SpaceRes updateSpace(Long id, SpaceReq updatedSpace, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("spaces", "can_update");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<SpaceEntity> existingSpaceOptional = spaceRepository.findById(id);

        if (existingSpaceOptional.isPresent()) {
            SpaceEntity existingSpace = existingSpaceOptional.get();

            existingSpace.setName(updatedSpace.getName());
            existingSpace.setLocation(updatedSpace.getLocation());
            existingSpace.setDescription(updatedSpace.getDescription());
            existingSpace.setUpdatedBy(user.getId());

            if (image != null && !image.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                existingSpace.setImage(imageUrl);
            }

            SpaceEntity savedSpace = spaceRepository.save(existingSpace);
            return transformUtil.convertToSpaceRes(savedSpace);
        } else {
            throw new RuntimeException("Space not found with id: " + id);
        }
    }

    public void deleteSpace(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_delete");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        spaceRepository.softDeleteById(id);
    }
}