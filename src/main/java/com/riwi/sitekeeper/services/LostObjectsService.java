package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.LostObjectsImgReq;
import com.riwi.sitekeeper.dtos.requests.LostObjectsReq;
import com.riwi.sitekeeper.dtos.responses.LostObjectsRes;
import com.riwi.sitekeeper.dtos.responses.LostObjectsSummaryRes;
import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import com.riwi.sitekeeper.repositories.LostObjectsRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LostObjectsService {

    @Autowired
    private LostObjectsRepository lostObjectsRepository;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired SpaceService spaceService;

    @Autowired
    private Cloudinary cloudinary;

    private final TransformUtil transformUtil;

    public LostObjectsService(TransformUtil transformUtil) {
        this.transformUtil = transformUtil;
    }

    public List<LostObjectsRes> getAllLostObjects(String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_read");
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

    public List<LostObjectsRes> getRecentlyClaimedObjects(String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        List<LostObjectsEntity> recentlyClaimedObjects = lostObjectsRepository.findRecentlyClaimedObjects(twoDaysAgo);
        return recentlyClaimedObjects.stream()
                .map(transformUtil::convertToLostObjectsRes)
                .collect(Collectors.toList());
    }

    public LostObjectsSummaryRes getLostObjectsSummary(String token) {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        long total = lostObjectsRepository.countByIsDeletedFalse();
        long claimedTotal = lostObjectsRepository.countByStatusAndIsDeletedFalse(LostObjectsStatus.RECLAMADO);
        long lostTotal = lostObjectsRepository.countByStatusAndIsDeletedFalse(LostObjectsStatus.PERDIDO);

        return new LostObjectsSummaryRes(total, claimedTotal, lostTotal);
    }

    public LostObjectsRes createLostObjects(LostObjectsReq lostObjects, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        LostObjectsImgReq lostImgReq = new LostObjectsImgReq(
                lostObjects.getName(),
                lostObjects.getDescription(),
                imageUrl,
                lostObjects.getSpaceId()
        );

        LostObjectsEntity newLostObjects = transformUtil.convertToLostObjectsEntity(lostImgReq);
        newLostObjects.setImage(imageUrl);
        newLostObjects.setCreatedBy(user.getId());
        newLostObjects.setUpdatedBy(user.getId());

        LostObjectsEntity savedLostObjects = lostObjectsRepository.save(newLostObjects);
        return transformUtil.convertToLostObjectsRes(savedLostObjects);
    }

    public LostObjectsRes updateLostObjects(Long id, LostObjectsReq updatedLostObjects, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("lostObjects", "can_update");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<LostObjectsEntity> existingLostObjectsOptional = lostObjectsRepository.findById(id);

        if (existingLostObjectsOptional.isPresent()) {
            LostObjectsEntity existingLostObjects = existingLostObjectsOptional.get();

            existingLostObjects.setName(updatedLostObjects.getName());
            existingLostObjects.setDescription(updatedLostObjects.getDescription());
            existingLostObjects.setUpdatedBy(user.getId());

            if (image != null && !image.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                existingLostObjects.setImage(imageUrl);
            }

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