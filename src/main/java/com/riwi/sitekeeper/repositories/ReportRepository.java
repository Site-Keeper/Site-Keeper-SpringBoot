package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends BaseRepository<ReportEntity> {

    long countByIsDeletedFalse();

    long countByStatusAndIsDeletedFalse(ReportStatus status);

}
