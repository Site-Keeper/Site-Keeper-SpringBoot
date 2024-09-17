package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.ReportEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ReportEntity rep SET rep.isDeleted = true WHERE rep.id = :id")
    void softDeleteById(@Param("id") Long id);

}
