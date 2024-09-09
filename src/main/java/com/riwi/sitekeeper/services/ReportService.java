package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<ReportEntity> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public ReportEntity createReport(ReportReq report) {

        ReportEntity request = ReportEntity.builder()
                .name(report.getName())
                .description(report.getDescription())
                .isEvent(report.getIsEvent())
                .image(report.getImage())
                .topicId(report.getTopicId())
                .theDate(report.getTheDate())
                .spaceId(report.getSpaceId())
                .build();

        return reportRepository.save(request);
    }

    public void deleteReport(Long id){
        reportRepository.deleteById(id);
    }
}
