package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
