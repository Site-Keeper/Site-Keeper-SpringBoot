package com.riwi.sitekeeper.repositories;

import com.riwi.sitekeeper.entitites.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
