package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.entitites.ReportEntity;
import com.riwi.sitekeeper.repositories.ReportRepository;
import com.riwi.sitekeeper.exceptions.reports.NotFoundException;
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

    private final TransformUtil transformUtil = new TransformUtil();

    public List<ReportRes> getAllReports(String token) {
        List<ReportEntity> reports = reportRepository.findAllByIsDeletedFalse();
        List<ReportRes> reportResList = new ArrayList<>();
        for (ReportEntity report : reports) {
            reportResList.add(transformUtil.convertToReportRes(report));
        }
        return reportResList;
    }

    public ReportRes getReportById(Long id, String token) {
        ReportEntity reportOptional = reportRepository.findById(id).orElseThrow(() -> new NotFoundException("Report could not be found by id"));
        return  transformUtil.convertToReportRes(reportOptional);
    }

    public ReportRes createReport(ReportReq report, String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);
        ReportEntity newReport = transformUtil.convertToReportEntity(report, token);
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