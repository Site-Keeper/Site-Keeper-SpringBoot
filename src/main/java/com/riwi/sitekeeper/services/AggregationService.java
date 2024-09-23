package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.dtos.requests.SpaceObjectsReq;
import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AggregationService {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ObjectService objectService;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired
    private Cloudinary cloudinary;

    public SpaceRes createSpaceAndObjects(SpaceObjectsReq spaceObjectsReq, MultipartFile image, String token) throws IOException {
        SpaceReq space = SpaceReq.builder()
                .name(spaceObjectsReq.getName())
                .location(spaceObjectsReq.getLocation())
                .description(spaceObjectsReq.getDescription())
                .build();

        SpaceRes createdSpace = spaceService.createSpace(space, image, token);

        System.out.println(createdSpace);

        for (ObjectReq objectReq : spaceObjectsReq.getObjects()) {
            ObjectReq newObject = new ObjectReq();
            newObject.setName(objectReq.getName());
            newObject.setDescription(objectReq.getDescription());
            newObject.setImage(objectReq.getImage());
            newObject.setSpaceId(createdSpace.getId());

            System.out.println(newObject);

            objectService.createObject(newObject, token);
        }

        Optional<SpaceRes> spaceRes = spaceService.getSpaceById(createdSpace.getId(), token);

        return spaceRes.orElse(null);

    }

}
