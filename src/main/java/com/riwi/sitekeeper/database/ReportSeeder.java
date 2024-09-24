package com.riwi.sitekeeper.database;

import com.riwi.sitekeeper.dtos.requests.ReportImgReq;
import com.riwi.sitekeeper.dtos.requests.ReportReq;
import com.riwi.sitekeeper.entities.ReportEntity;
import com.riwi.sitekeeper.enums.ReportStatus;
import com.riwi.sitekeeper.repositories.ReportRepository;
import com.riwi.sitekeeper.utils.TransformUtil;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class ReportSeeder {
    //Logger to see seed action
    public Logger logger = Logger.getLogger(String.valueOf(ReportSeeder.class));
    //Report Repository to insert data into db
    public ReportRepository reportRepository;
    //Transform util to make the req go through
    public TransformUtil transformUtil;

    public ReportSeeder(ReportRepository reportRepository){
        this.reportRepository=reportRepository;
    }

    private void seedReportsTable(){
        ReportImgReq seed1 = new ReportImgReq();
        seed1.setName("Limpieza");
        seed1.setDescription("Jugo de mango regado en el piso");
        seed1.setTopicId(1L);
        seed1.setIsEvent(false);
        seed1.setTheDate(LocalDateTime.now());
        seed1.setSpaceId(6l);

        ReportImgReq seed2 = new ReportImgReq();
        seed2.setName("Reguero de café");
        seed2.setDescription("Requero de Café en el piso");
        seed2.setTopicId(1l);
        seed2.setIsEvent(false);
        seed2.setTheDate(LocalDateTime.now());
        seed2.setSpaceId(2l);

        ReportImgReq seed3 = new ReportImgReq();
        seed3.setName("PC sin MySql");
        seed3.setDescription("pc #10 sin funcionamiento correcto de MySql workbench");
        seed3.setTopicId(2l);
        seed3.setIsEvent(false);
        seed3.setTheDate(LocalDateTime.now());
        seed3.setSpaceId(4l);

        ReportImgReq seed4 = new ReportImgReq();
        seed4.setName("Conferencia Java");
        seed4.setDescription("Conferencia Java en Review 2");
        seed4.setTopicId(3l);
        seed4.setIsEvent(true);
        seed4.setTheDate(LocalDateTime.now());
        seed4.setSpaceId(8l);

        reportRepository.save(transformUtil.convertToReportEntity(seed1));
        reportRepository.save(transformUtil.convertToReportEntity(seed2));
        reportRepository.save(transformUtil.convertToReportEntity(seed3));
        reportRepository.save(transformUtil.convertToReportEntity(seed4));
    }


}
