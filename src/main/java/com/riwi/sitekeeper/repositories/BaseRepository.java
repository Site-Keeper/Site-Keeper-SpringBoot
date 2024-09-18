package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.Auditable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository <T extends Auditable> extends JpaRepository<T, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.isDeleted = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    List<T> findAllByIsDeletedFalse();

}
