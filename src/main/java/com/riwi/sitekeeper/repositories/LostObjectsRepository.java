package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.LostObjectsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LostObjectsRepository extends BaseRepository<LostObjectsEntity>{
}
