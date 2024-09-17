package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.ObjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectEntity, Long> {

    Optional<ObjectEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE ObjectEntity O ob SET ob.isDeleted = true WHERE ob.id = :id")
    void softDeleteById(@Param("id") Long id);

}
