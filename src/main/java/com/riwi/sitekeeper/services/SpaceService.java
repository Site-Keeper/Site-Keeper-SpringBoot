package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.dtos.requests.SpaceReq;
import com.riwi.sitekeeper.dtos.responses.SpaceRes;
import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    public List<SpaceRes> getAllSpaces() {
        List<SpaceEntity> spaces = spaceRepository.findAll();
        List<SpaceRes> spaceResList = new ArrayList<>();
        for (SpaceEntity space : spaces) {
            spaceResList.add(convertToSpaceRes(space));
        }
        return spaceResList;
    }

    public Optional<SpaceRes> getSpaceById(Long id) {
        Optional<SpaceEntity> spaceOptional = spaceRepository.findById(id);
        return spaceOptional.map(this::convertToSpaceRes);
    }

    public Optional<SpaceRes> getSpaceByName(String name) {
        Optional<SpaceEntity> spaceOptional = spaceRepository.findByName(name);
        return spaceOptional.map(this::convertToSpaceRes);
    }

    public SpaceRes createSpace(SpaceReq space, String token) {
        SpaceEntity newSpace = convertToSpaceEntity(space);
        SpaceEntity savedSpace = spaceRepository.save(newSpace);
        return convertToSpaceRes(savedSpace);
    }

    public SpaceRes updateSpace(Long id, SpaceReq updatedSpace) {
        Optional<SpaceEntity> existingSpaceOptional = spaceRepository.findById(id);

        if (existingSpaceOptional.isPresent()) {
            SpaceEntity existingSpace = existingSpaceOptional.get();
            updateSpaceEntity(existingSpace, updatedSpace);
            SpaceEntity savedSpace = spaceRepository.save(existingSpace);
            return convertToSpaceRes(savedSpace);
        } else {
            throw new RuntimeException("Space not found with id: " + id);
        }
    }

    public void deleteSpace(Long id) {
        spaceRepository.softDeleteById(id);
    }

    private SpaceEntity convertToSpaceEntity(SpaceReq spaceReq) {
        return SpaceEntity.builder()
                .name(spaceReq.getName())
                .location(spaceReq.getLocation())
                .description(spaceReq.getDescription())
                .image(spaceReq.getImage())
                .build();
    }

    private SpaceRes convertToSpaceRes(SpaceEntity spaceEntity) {
        return SpaceRes.builder()
                .id(spaceEntity.getId())
                .name(spaceEntity.getName())
                .location(spaceEntity.getLocation())
                .description(spaceEntity.getDescription())
                .image(spaceEntity.getImage())
                .build();
    }

    private void updateSpaceEntity(SpaceEntity existingSpace, SpaceReq updatedSpace) {
        existingSpace.setName(updatedSpace.getName());
        existingSpace.setLocation(updatedSpace.getLocation());
        existingSpace.setDescription(updatedSpace.getDescription());
        existingSpace.setImage(updatedSpace.getImage());
    }
}