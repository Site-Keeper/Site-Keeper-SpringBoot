package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    public SpaceEntity create (SpaceEntity space){
        SpaceEntity newSpace = new SpaceEntity();
        newSpace.setName(space.getName());
        newSpace.setLocation(space.getLocation());
        newSpace.setDescription(space.getDescription());
        newSpace.setImage(space.getImage());
        newSpace.setCreatedAt(LocalDateTime.now());
        newSpace.setUpdatedAt(LocalDateTime.now());
        return spaceRepository.save(newSpace);
    }

    public SpaceEntity update(SpaceEntity space){
        SpaceEntity updatedSpace = new SpaceEntity();
        updatedSpace.setId(space.getId());
        updatedSpace.setName(space.getName());
        updatedSpace.setLocation(space.getLocation());
        updatedSpace.setDescription(space.getDescription());
        updatedSpace.setImage(space.getImage());
        updatedSpace.setCreatedAt(space.getCreatedAt());
        updatedSpace.setUpdatedAt(LocalDateTime.now());
        return spaceRepository.save(space);
    }

    public List<SpaceEntity> getAll(){
        return spaceRepository.findAll();
    }

    public Optional<SpaceEntity> getById(Long id){
        return spaceRepository.findById(id);
    }
}
