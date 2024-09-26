package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entities.ObjectEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectRepository extends BaseRepository<ObjectEntity> {

    Optional<ObjectEntity> findByName(String name);

}
