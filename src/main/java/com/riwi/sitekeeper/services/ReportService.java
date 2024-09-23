package com.riwi.sitekeeper.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.riwi.sitekeeper.clients.NestServiceClient;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import com.riwi.sitekeeper.dtos.requests.ReportImgReq;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.dtos.responses.ReportRes;
import com.riwi.sitekeeper.dtos.responses.ReportSummaryRes;
import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import com.riwi.sitekeeper.repositories.ReportRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private NestServiceClient nestServiceClient;

    @Autowired
    private Cloudinary cloudinary;

    private final TransformUtil transformUtil;

    public ReportService(TransformUtil transformUtil) {
        this.transformUtil = transformUtil;
    }

    public List<ReportRes> getAllReports(String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        List<ReportEntity> reports = reportRepository.findAllByIsDeletedFalse();
        List<ReportRes> reportResList = new ArrayList<>();
        for (ReportEntity report : reports) {
            reportResList.add(transformUtil.convertToReportRes(report));
        }
        return reportResList;
    }

    public Optional<ReportRes> getReportById(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_read");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

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

    public ReportRes createReport(ReportReq report, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("reports", "can_create");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        String imageUrl;

        ReportImgReq reportImgReq = ReportImgReq.builder()
                .name(report.getName())
                .description(report.getDescription())
                .isEvent(report.getIsEvent())
                .topicId(report.getTopicId())
                .theDate(report.getTheDate())
                .spaceId(report.getSpaceId())
                .build();


        if (image != null) {

            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("url");
            reportImgReq.setImage(imageUrl);
        }

        System.out.println("reportImgReq = " + reportImgReq);

        ReportEntity newReport = transformUtil.convertToReportEntity(reportImgReq);

        newReport.setCreatedBy(user.getId());
        newReport.setUpdatedBy(user.getId());
        ReportEntity savedReport = reportRepository.save(newReport);
        return transformUtil.convertToReportRes(savedReport);
    }

    public ReportRes updateReport(Long id, ReportReq updatedReport, MultipartFile image, String token) throws IOException {
        ValidationReq validationReq = new ValidationReq("reports", "can_update");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        Optional<ReportEntity> existingReportOptional = reportRepository.findById(id);
        String imageUrl;

        ReportImgReq reportImgReq = ReportImgReq.builder()
                .name(updatedReport.getName())
                .description(updatedReport.getDescription())
                .isEvent(updatedReport.getIsEvent())
                .topicId(updatedReport.getTopicId())
                .theDate(updatedReport.getTheDate())
                .spaceId(updatedReport.getSpaceId())
                .build();


        if (image != null) {

            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("url");
            reportImgReq.setImage(imageUrl);
        }

        if (existingReportOptional.isPresent()) {
            ReportEntity existingReport = existingReportOptional.get();
            transformUtil.updateReportEntity(existingReport, reportImgReq);
            ReportEntity savedReport = reportRepository.save(existingReport);
            return transformUtil.convertToReportRes(savedReport);
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    public void deleteReport(Long id, String token) {
        ValidationReq validationReq = new ValidationReq("reports", "can_delete");
        ValidationUserRes user = nestServiceClient.checkPermission(validationReq, token);

        reportRepository.softDeleteById(id);
    }
}