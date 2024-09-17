package com.riwi.sitekeeper.services;

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

    public List<LostObjectsRes> getAllLostObjects() {
        List<LostObjectsEntity> lostObjects = lostObjectsRepository.findAll();
        List<LostObjectsRes> lostObjectsResList = new ArrayList<>();
        for (LostObjectsEntity lostObject : lostObjects) {
            lostObjectsResList.add(convertToLostObjectsRes(lostObject));
        }
        return lostObjectsResList;
    }

    public Optional<LostObjectsRes> getLostObjectsById(Long id) {
        Optional<LostObjectsEntity> lostObjectsOptional = lostObjectsRepository.findById(id);
        return lostObjectsOptional.map(this::convertToLostObjectsRes);
    }

    public LostObjectsRes createLostObjects(LostObjectsReq lostObjects) {
        LostObjectsEntity newLostObjects = convertToLostObjectsEntity(lostObjects);
        LostObjectsEntity savedLostObjects = lostObjectsRepository.save(newLostObjects);
        return convertToLostObjectsRes(savedLostObjects);
    }

    public LostObjectsRes updateLostObjects(Long id, LostObjectsReq updatedLostObjects) {
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

    public void deleteLostObjects(Long id) {
        lostObjectsRepository.deleteById(id);
    }

    private LostObjectsEntity convertToLostObjectsEntity(LostObjectsReq lostObjectsReq) {
        // Implementation left empty as requested
        return LostObjectsEntity.builder()
                .name(lostObjectsReq.getName())
                .description(lostObjectsReq.getDescription())
                .image(lostObjectsReq.getImage())
                .spaceId(lostObjectsReq.getSpaceId())
                .build();
    }

    private LostObjectsRes convertToLostObjectsRes(LostObjectsEntity lostObjectsEntity) {
        // Implementation left empty as requested
        return new LostObjectsRes();
    }

    private void updateLostObjectsEntity(LostObjectsEntity existingLostObjects, LostObjectsReq updatedLostObjects) {
        // Implementation left empty as requested
    }
}