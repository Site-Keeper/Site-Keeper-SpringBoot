package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
}
