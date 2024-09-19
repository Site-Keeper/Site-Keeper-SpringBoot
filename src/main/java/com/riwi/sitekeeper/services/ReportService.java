package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportRes> getAllReports(String token) {
        List<ReportEntity> reports = reportRepository.findAllByIsDeletedFalse();
        List<ReportRes> reportResList = new ArrayList<>();
        for (ReportEntity report : reports) {
            reportResList.add(convertToReportRes(report));
        }
        return reportResList;
    }

    public Optional<ReportRes> getReportById(Long id, String token) {
        Optional<ReportEntity> reportOptional = reportRepository.findById(id);
        return reportOptional.map(this::convertToReportRes);
    }

    public ReportRes createReport(ReportReq report, String token) {
        ReportEntity newReport = convertToReportEntity(report);
        ReportEntity savedReport = reportRepository.save(newReport);
        return convertToReportRes(savedReport);
    }

    public ReportRes updateReport(Long id, ReportReq updatedReport, String token) {
        Optional<ReportEntity> existingReportOptional = reportRepository.findById(id);

        if (existingReportOptional.isPresent()) {
            ReportEntity existingReport = existingReportOptional.get();
            updateReportEntity(existingReport, updatedReport);
            ReportEntity savedReport = reportRepository.save(existingReport);
            return convertToReportRes(savedReport);
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    public void deleteReport(Long id, String token) {
        reportRepository.softDeleteById(id);
    }

    private ReportEntity convertToReportEntity(ReportReq reportReq) {
        return ReportEntity.builder()
                .name(reportReq.getName())
                .description(reportReq.getDescription())
                .isEvent(reportReq.getIsEvent())
                .image(reportReq.getImage())
                .topicId(reportReq.getTopicId())
                .theDate(reportReq.getTheDate())
                .spaceId(reportReq.getSpaceId())
                .build();
    }

    private ReportRes convertToReportRes(ReportEntity reportEntity) {
        return ReportRes.builder()
                .id(reportEntity.getId())
                .name(reportEntity.getName())
                .description(reportEntity.getDescription())
                .isEvent(reportEntity.getIsEvent())
                .image(reportEntity.getImage())
                .topicId(reportEntity.getTopicId())
                .theDate(reportEntity.getTheDate())
                .spaceId(reportEntity.getSpaceId())
                .build();
    }

    private void updateReportEntity(ReportEntity existingReport, ReportReq updatedReport) {
        existingReport.setName(updatedReport.getName());
        existingReport.setDescription(updatedReport.getDescription());
        existingReport.setIsEvent(updatedReport.getIsEvent());
        existingReport.setImage(updatedReport.getImage());
        existingReport.setTopicId(updatedReport.getTopicId());
        existingReport.setTheDate(updatedReport.getTheDate());
        existingReport.setSpaceId(updatedReport.getSpaceId());
    }
}