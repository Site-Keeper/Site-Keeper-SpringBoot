package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends BaseRepository<ReportEntity> {

    long countByIsDeletedFalse();

    long countByStatusAndIsDeletedFalse(ReportStatus status);

    List<ReportEntity> findTop5ByIsDeletedFalseOrderByCreatedAtDesc();

    List<ReportEntity> findAllByTopicIdAndIsDeletedFalse(Long topicId);

    List<ReportEntity> findAllByIsEventAndIsDeletedFalse(Boolean isEvent);

}
