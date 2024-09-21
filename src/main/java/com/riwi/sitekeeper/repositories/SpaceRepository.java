package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends BaseRepository<SpaceEntity>{

    Optional<SpaceEntity> findByName(String name);

    @Query("SELECT s FROM SpaceEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) AND s.isDeleted = false")
    List<SpaceEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}
