package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    private final TransformUtil transformUtil = new TransformUtil();

    public List<SpaceRes> getAllSpaces(String token) {
        List<SpaceEntity> spaces = spaceRepository.findAllByIsDeletedFalse();
        List<SpaceRes> spaceResList = new ArrayList<>();
        for (SpaceEntity space : spaces) {
            spaceResList.add(transformUtil.convertToSpaceRes(space));
        }
        return spaceResList;
    }

    public Optional<SpaceRes> getSpaceById(Long id, String token) {
        Optional<SpaceEntity> spaceOptional = spaceRepository.findById(id);
        return spaceOptional.map(transformUtil::convertToSpaceRes);
    }

    public Optional<SpaceEntity> getSpaceById(Long id) {
        return spaceRepository.findById(id);
    }

    public Optional<SpaceRes> getSpaceByName(String name, String token) {
        Optional<SpaceEntity> spaceOptional = spaceRepository.findByName(name);
        return spaceOptional.map(transformUtil::convertToSpaceRes);
    }

    public SpaceRes createSpace(SpaceReq space, String token) {
        ValidationReq validationReq = new ValidationReq("spaces", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
        SpaceEntity newSpace = transformUtil.convertToSpaceEntity(space);
        newSpace.setCreatedBy(user.getId());
        newSpace.setUpdatedBy(user.getId());
        SpaceEntity savedSpace = spaceRepository.save(newSpace);
        return transformUtil.convertToSpaceRes(savedSpace);
    }

    public SpaceRes updateSpace(Long id, SpaceReq updatedSpace, String token) {
        Optional<SpaceEntity> existingSpaceOptional = spaceRepository.findById(id);

        if (existingSpaceOptional.isPresent()) {
            SpaceEntity existingSpace = existingSpaceOptional.get();
            transformUtil.updateSpaceEntity(existingSpace, updatedSpace);
            SpaceEntity savedSpace = spaceRepository.save(existingSpace);
            return transformUtil.convertToSpaceRes(savedSpace);
        } else {
            throw new RuntimeException("Space not found with id: " + id);
        }
    }

    public void deleteSpace(Long id, String token) {
        spaceRepository.softDeleteById(id);
    }
}