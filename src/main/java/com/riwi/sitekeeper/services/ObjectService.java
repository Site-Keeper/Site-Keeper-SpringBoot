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
        return objectRepository.save(objectEntity);
    }

    public void deleteObject(Long id) {
        objectRepository.deleteById(id);
    }

}
