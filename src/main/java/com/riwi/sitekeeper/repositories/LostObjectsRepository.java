package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostObjectsRepository extends JpaRepository<LostObjectsEntity, Long> {

}
