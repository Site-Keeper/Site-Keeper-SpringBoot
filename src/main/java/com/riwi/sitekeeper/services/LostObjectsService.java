package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.repositories.LostObjectsRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
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

    private final TransformUtil transformUtil = new TransformUtil();

    public List<LostObjectsRes> getAllLostObjects(String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects    ", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<LostObjectsEntity> lostObjects = lostObjectsRepository.findAllByIsDeletedFalse();
        List<LostObjectsRes> lostObjectsResList = new ArrayList<>();
        for (LostObjectsEntity lostObject : lostObjects) {
            lostObjectsResList.add(transformUtil.convertToLostObjectsRes(lostObject));
        }
        return lostObjectsResList;
    }

    public Optional<LostObjectsRes> getLostObjectsById(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
        Optional<LostObjectsEntity> lostObjectsOptional = lostObjectsRepository.findById(id);
        return lostObjectsOptional.map(transformUtil::convertToLostObjectsRes);
    }

    public LostObjectsRes createLostObjects(LostObjectsReq lostObjects, String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
        LostObjectsEntity newLostObjects = transformUtil.convertToLostObjectsEntity(lostObjects);
        newLostObjects.setCreatedBy(user.getId());
        newLostObjects.setUpdatedBy(user.getId());
        LostObjectsEntity savedLostObjects = lostObjectsRepository.save(newLostObjects);
        return transformUtil.convertToLostObjectsRes(savedLostObjects);
    }

    public LostObjectsRes updateLostObjects(Long id, LostObjectsReq updatedLostObjects, String token) {
        Optional<LostObjectsEntity> existingLostObjectsOptional = lostObjectsRepository.findById(id);

        if (existingLostObjectsOptional.isPresent()) {
            LostObjectsEntity existingLostObjects = existingLostObjectsOptional.get();
            transformUtil.updateLostObjectsEntity(existingLostObjects, updatedLostObjects);
            LostObjectsEntity savedLostObjects = lostObjectsRepository.save(existingLostObjects);
            return transformUtil.convertToLostObjectsRes(savedLostObjects);
        } else {
            throw new RuntimeException("LostObjects not found with id: " + id);
        }
    }

    public void deleteLostObjects(Long id, String token) {
        lostObjectsRepository.softDeleteById(id);
    }
}