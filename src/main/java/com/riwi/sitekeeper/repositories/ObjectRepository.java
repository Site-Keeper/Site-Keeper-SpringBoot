package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectEntity, Long> {

}
