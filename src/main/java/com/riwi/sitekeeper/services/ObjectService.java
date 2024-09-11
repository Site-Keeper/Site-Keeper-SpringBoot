package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.entitites.ObjectEntity;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    public List<ObjectEntity> getAllObjects() {

        return objectRepository.findAll();
    }

    public Optional<ObjectEntity> getObjectById(Long id) {

        return objectRepository.findById(id);
    }

    public ObjectEntity createObject(ObjectEntity objectEntity) {

        ObjectEntity newObjectEntity = new ObjectEntity();

        newObjectEntity.setName(objectEntity.getName());
        newObjectEntity.setDescription(objectEntity.getDescription());
        newObjectEntity.setImage(objectEntity.getName());
        newObjectEntity.setSpaceId(objectEntity.getSpaceId());

        return objectRepository.save(objectEntity);

    }

    public ObjectEntity update(ObjectEntity objectEntity) {

        ObjectEntity updateObjectEntity = new ObjectEntity();

        updateObjectEntity.setId(objectEntity.getId());
        updateObjectEntity.setName(objectEntity.getName());
        updateObjectEntity.setDescription(objectEntity.getDescription());
        updateObjectEntity.setImage(objectEntity.getImage());
        updateObjectEntity.setSpaceId(objectEntity.getSpaceId());

        return objectRepository.save(objectEntity);

    }

    public void deleteObject(Long id) {

        objectRepository.deleteById(id);
    }

}
