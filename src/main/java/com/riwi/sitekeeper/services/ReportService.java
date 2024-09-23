package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.ReportSummaryRes;
import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import com.riwi.sitekeeper.repositories.ReportRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private NestServiceClient nestServiceClient;

    private final TransformUtil transformUtil;

    public ReportService(TransformUtil transformUtil) {
        this.transformUtil = transformUtil;
    }

    public List<ReportRes> getAllReports(String token) {
        List<ReportEntity> reports = reportRepository.findAllByIsDeletedFalse();
        List<ReportRes> reportResList = new ArrayList<>();
        for (ReportEntity report : reports) {
            reportResList.add(transformUtil.convertToReportRes(report));
        }
        return reportResList;
    }

    public Optional<ReportRes> getReportById(Long id, String token) {
        Optional<ReportEntity> reportOptional = reportRepository.findById(id);
        return reportOptional.map(transformUtil::convertToReportRes);
    }

    public ReportSummaryRes getReportSummary(String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        long total = reportRepository.count();
        long approvedTotal = reportRepository.countByStatusAndIsDeletedFalse(ReportStatus.APPROVED);
        long rejectedTotal = reportRepository.countByStatusAndIsDeletedFalse(ReportStatus.REJECTED);
        ReportSummaryRes reportSummaryRes = new ReportSummaryRes();
        reportSummaryRes.setTotal(total);
        reportSummaryRes.setApprovedTotal(approvedTotal);
        reportSummaryRes.setRejectedTotal(rejectedTotal);
        return reportSummaryRes;
    }

    public ReportRes createReport(ReportReq report, String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
        ReportEntity newReport = transformUtil.convertToReportEntity(report);
        newReport.setCreatedBy(user.getId());
        newReport.setUpdatedBy(user.getId());
        ReportEntity savedReport = reportRepository.save(newReport);
        return transformUtil.convertToReportRes(savedReport);
    }

    public ReportRes updateReport(Long id, ReportReq updatedReport, String token) {
        Optional<ReportEntity> existingReportOptional = reportRepository.findById(id);

        if (existingReportOptional.isPresent()) {
            ReportEntity existingReport = existingReportOptional.get();
            transformUtil.updateReportEntity(existingReport, updatedReport);
            ReportEntity savedReport = reportRepository.save(existingReport);
            return transformUtil.convertToReportRes(savedReport);
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    public void deleteReport(Long id, String token) {
        reportRepository.softDeleteById(id);
    }
}