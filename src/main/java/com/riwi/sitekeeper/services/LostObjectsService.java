package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.repositories.LostObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LostObjectsService {

    @Autowired
    private LostObjectsRepository lostObjectsRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired SpaceService spaceService;

    public List<LostObjectsRes> getAllLostObjects(String token) {
        ValidationReq validationReq = new ValidationReq("LostObjects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<LostObjectsEntity> lostObjects = lostObjectsRepository.findAllByIsDeletedFalse();
        List<LostObjectsRes> lostObjectsResList = new ArrayList<>();
        for (LostObjectsEntity lostObject : lostObjects) {
            lostObjectsResList.add(convertToLostObjectsRes(lostObject));
        }
        return lostObjectsResList;
    }

    public Optional<LostObjectsRes> getLostObjectsById(Long id, String token) {
        Optional<LostObjectsEntity> lostObjectsOptional = lostObjectsRepository.findById(id);
        return lostObjectsOptional.map(this::convertToLostObjectsRes);
    }

    public LostObjectsRes createLostObjects(LostObjectsReq lostObjects, String token) {
        LostObjectsEntity newLostObjects = convertToLostObjectsEntity(lostObjects);
        LostObjectsEntity savedLostObjects = lostObjectsRepository.save(newLostObjects);
        return convertToLostObjectsRes(savedLostObjects);
    }

    public LostObjectsRes updateLostObjects(Long id, LostObjectsReq updatedLostObjects, String token) {
        Optional<LostObjectsEntity> existingLostObjectsOptional = lostObjectsRepository.findById(id);

        if (existingLostObjectsOptional.isPresent()) {
            LostObjectsEntity existingLostObjects = existingLostObjectsOptional.get();
            updateLostObjectsEntity(existingLostObjects, updatedLostObjects);
            LostObjectsEntity savedLostObjects = lostObjectsRepository.save(existingLostObjects);
            return convertToLostObjectsRes(savedLostObjects);
        } else {
            throw new RuntimeException("LostObjects not found with id: " + id);
        }
    }

    public void deleteLostObjects(Long id, String token) {
        lostObjectsRepository.softDeleteById(id);
    }

    private LostObjectsEntity convertToLostObjectsEntity(LostObjectsReq lostObjectsReq) {
        // Implementation left empty as requested
        return LostObjectsEntity.builder()
                .name(lostObjectsReq.getName())
                .description(lostObjectsReq.getDescription())
                .image(lostObjectsReq.getImage())
                .spaceId(spaceService.getSpaceById(lostObjectsReq.getSpaceId()).get())
                .build();
    }

    private LostObjectsRes convertToLostObjectsRes(LostObjectsEntity lostObjectsEntity) {
        return LostObjectsRes.builder()
                .id(lostObjectsEntity.getId())
                .name(lostObjectsEntity.getName())
                .description(lostObjectsEntity.getDescription())
                .image(lostObjectsEntity.getImage())
                .spaceId(lostObjectsEntity.getSpaceId().getId())
                .status(lostObjectsEntity.getStatus())
                .build();
    }

    private void updateLostObjectsEntity(LostObjectsEntity existingLostObjects, LostObjectsReq updatedLostObjects) {
        existingLostObjects.setName(updatedLostObjects.getName());
        existingLostObjects.setDescription(updatedLostObjects.getDescription());
        existingLostObjects.setImage(updatedLostObjects.getImage());
        existingLostObjects.setSpaceId(spaceService.getSpaceById(updatedLostObjects.getSpaceId()).get());
        existingLostObjects.setStatus(updatedLostObjects.getStatus());
    }
}