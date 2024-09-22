package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends BaseRepository<ReportEntity> {

    long countByIsDeletedFalse();

    long countByStatusAndIsDeletedFalse(ReportStatus status);

}
