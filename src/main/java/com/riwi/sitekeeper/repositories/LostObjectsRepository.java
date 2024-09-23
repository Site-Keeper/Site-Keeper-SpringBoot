package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entities.LostObjectsEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LostObjectsRepository extends BaseRepository<LostObjectsEntity>{

    @Query("SELECT l FROM LostObjectsEntity l WHERE l.status = 'RECLAMADO' AND l.updatedAt >= :twoDaysAgo")
    List<LostObjectsEntity> findRecentlyClaimedObjects(@Param("twoDaysAgo") LocalDateTime twoDaysAgo);

    long countByIsDeletedFalse();

    long countByStatusAndIsDeletedFalse(LostObjectsStatus status);

}
