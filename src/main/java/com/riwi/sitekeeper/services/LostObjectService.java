package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import com.riwi.sitekeeper.repositories.LostObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class LostObjectService {

    @Autowired
    private LostObjectsRepository lostObjectsRepository;

    public List<LostObjectsEntity> getAllLostObjects() {

        return lostObjectsRepository.findAll();

    }

    public Optional<LostObjectsEntity> getLostObjectById(Long id) {

        return lostObjectsRepository.findById(id);

    }

    public LostObjectsEntity createLostObject(LostObjectsEntity lostObjectsEntity) {

        LostObjectsEntity newLostObjectsEntity = new LostObjectsEntity();

        newLostObjectsEntity.setName(lostObjectsEntity.getName());
        newLostObjectsEntity.setDescription(lostObjectsEntity.getDescription());
        newLostObjectsEntity.setImage(lostObjectsEntity.getImage());
        newLostObjectsEntity.setSpaceId(lostObjectsEntity.getSpaceId());

        return lostObjectsRepository.save(lostObjectsEntity);

    }

    public LostObjectsEntity update(LostObjectsEntity lostObjectsEntity) {

        LostObjectsEntity updateLostObjectsEntity = new LostObjectsEntity();

        updateLostObjectsEntity.setName(lostObjectsEntity.getName());
        updateLostObjectsEntity.setDescription(lostObjectsEntity.getDescription());
        updateLostObjectsEntity.setImage(lostObjectsEntity.getImage());
        updateLostObjectsEntity.setSpaceId(lostObjectsEntity.getSpaceId());

        return lostObjectsRepository.save(lostObjectsEntity);

    }

    public void deleteLostObject(Long id) {

        lostObjectsRepository.deleteById(id);

    }

}
