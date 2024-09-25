package com.riwi.sitekeeper.database;

import com.riwi.sitekeeper.dtos.requests.LostObjectSeederReq;
import com.riwi.sitekeeper.dtos.requests.ObjectSeederReq;
import com.riwi.sitekeeper.dtos.requests.ReportImgReq;
import com.riwi.sitekeeper.dtos.requests.ReportSeederReq;
import com.riwi.sitekeeper.entities.LostObjectsEntity;
import com.riwi.sitekeeper.entities.SpaceEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import com.riwi.sitekeeper.enums.ReportStatus;
import com.riwi.sitekeeper.repositories.LostObjectsRepository;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import com.riwi.sitekeeper.repositories.ReportRepository;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class Seeder {
    //Logger to see seed action
    public Logger logger = Logger.getLogger(String.valueOf(Seeder.class));
    //Spaces Repository to insert data into db
    public SpaceRepository spaceRepository;
    //Object Repository to insert data into db
    public ObjectRepository objectRepository;
    //Report Repository to insert data into db
    public ReportRepository reportRepository;
    //Lost objects Repository to inject into db
    public LostObjectsRepository lostObjectsRepository;
    //TransformUtil to make injection effective
    public TransformUtil transformUtil;
    @Autowired
    public Seeder (
            SpaceRepository spaceRepository,
            ObjectRepository objectRepository,
            ReportRepository reportRepository,
            LostObjectsRepository lostObjectsRepository,
            TransformUtil transformUtil){
        this.spaceRepository = spaceRepository;
        this.objectRepository = objectRepository;
        this.transformUtil = transformUtil;
        this.reportRepository = reportRepository;
        this.lostObjectsRepository = lostObjectsRepository;
    }

    private void seedSpacesTable(){
        try {
            SpaceEntity seed1 = new SpaceEntity();
            seed1.setId(2l);
            seed1.setName("Recepción");
            seed1.setLocation("Tercer piso CC de Moda Outlet - Entrada a Riwi");
            seed1.setDescription("La recepción cuenta con un diseño moderno y acogedor. Un mostrador de madera clara da la bienvenida a coders, colaboradores y visitantes");
            seed1.setCreatedBy(1l);
            seed1.setUpdatedBy(1l);
            seed1.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727122181/volwjrgzfn8jjvw1axkz.jpg");

            SpaceEntity seed2 = new SpaceEntity();
            seed2.setId(3l);
            seed2.setName("Entrada 1");
            seed2.setLocation("Piso 3 - Ala Occidental - Después de los torniquetes");
            seed2.setDescription("Zona de esparcimiento donde se pueden apreciar las alianzas de RIWI");
            seed2.setCreatedBy(1l);
            seed2.setUpdatedBy(1l);
            seed2.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727122534/jhouwzby2u322h42axy3.jpg");

            SpaceEntity seed3 = new SpaceEntity();
            seed3.setId(4l);
            seed3.setName("Entrada 2");
            seed3.setLocation("Piso 3 - Ala Occidental - en medio de la entrada y zona de review");
            seed3.setDescription("Zona de esparcimiento");
            seed3.setCreatedBy(1l);
            seed3.setUpdatedBy(1l);
            seed3.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727122733/rvwxiw4gl0us1pjrzxny.jpg");

            SpaceEntity seed4 = new SpaceEntity();
            seed4.setId(5l);
            seed4.setName("Cubo Azul 1");
            seed4.setLocation("Piso 3 - Ala Occidental - en medio de la entrada y zona de review");
            seed4.setDescription("Zona de trabajo insonorizada para mayor concentración");
            seed4.setCreatedBy(1l);
            seed4.setUpdatedBy(1l);
            seed4.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727122903/ba6kqaif9ya5sq6vx3oe.jpg");

            SpaceEntity seed5 = new SpaceEntity();
            seed5.setId(6l);
            seed5.setName("Cubo Azul 2");
            seed5.setLocation("Piso 3 - Ala Occidental - en medio de la entrada y zona de review en el lado opuesto del cubo azul 1");
            seed5.setDescription("Zona de trabajo insonorizada para mayor concentración");
            seed5.setCreatedBy(1l);
            seed5.setUpdatedBy(1l);
            seed5.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727122976/ij136ardsszagt8azckt.jpg");

            SpaceEntity seed6 = new SpaceEntity();
            seed6.setId(7l);
            seed6.setName("Review 1");
            seed6.setLocation("Piso 3 - Ala Occidental - Justo detras de los cubos azules");
            seed6.setDescription("Zona Para coders");
            seed6.setCreatedBy(1l);
            seed6.setUpdatedBy(1l);
            seed6.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727123814/co1zrlfodete4bjcqlyq.jpg");

            SpaceEntity seed7 = new SpaceEntity();
            seed7.setId(8l);
            seed7.setName("Review 2");
            seed7.setLocation("Piso 3 - Ala Occidental - Al lado del Review 1");
            seed7.setDescription("Zona para coders");
            seed7.setCreatedBy(1l);
            seed7.setUpdatedBy(1l);
            seed7.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727124077/d4djrpnxz3tvuswtzvhl.jpg");

            SpaceEntity seed8 = new SpaceEntity();
            seed8.setId(9l);
            seed8.setName("Gradas");
            seed8.setLocation("Centro de RIWI");
            seed8.setDescription("Centro y Corazón de riwi, lugar destinado a eventos, conferencias y esparcimiento de Coders en sus espacios libres");
            seed8.setCreatedBy(1l);
            seed8.setUpdatedBy(1l);
            seed8.setImage("http://res.cloudinary.com/dc5yzuyby/image/upload/v1727124380/iib9pkz5va7kwzqp7bnt.jpg");

            spaceRepository.save(seed1);
            spaceRepository.save(seed2);
            spaceRepository.save(seed3);
            spaceRepository.save(seed4);
            spaceRepository.save(seed5);
            spaceRepository.save(seed6);
            spaceRepository.save(seed7);
            spaceRepository.save(seed8);

        }catch (Exception e){
            logger.warning("Spaces Seeding Not Required"+e);
        }

    }
    private void seedObjectTable(){
        try {
            ObjectSeederReq seed1 = new ObjectSeederReq();
            seed1.setName("Silla Ergonómica");
            seed1.setDescription("Silla egonómica marca MUMA");
            seed1.setImage("EventSeatOutlinedIcon");
            seed1.setCreatedBy(1l);
            seed1.setUpdatedBy(1l);
            seed1.setSpaceId(7l);

            ObjectSeederReq seed2 = new ObjectSeederReq();
            seed2.setName("PC");
            seed2.setDescription("PC marca Compumax");
            seed2.setImage("ComputerOutlinedIcon");
            seed2.setCreatedBy(1l);
            seed2.setUpdatedBy(1l);
            seed2.setSpaceId(8l);

            ObjectSeederReq seed3 = new ObjectSeederReq();
            seed3.setName("Silla Ergonómica");
            seed3.setDescription("Silla egonómica marca MUMA");
            seed3.setImage("EventSeatOutlinedIcon");
            seed3.setCreatedBy(1l);
            seed3.setUpdatedBy(1l);
            seed3.setSpaceId(8l);

            ObjectSeederReq seed4 = new ObjectSeederReq();
            seed4.setName("PC");
            seed4.setDescription("PC marca Compumax");
            seed4.setImage("ComputerOutlinedIcon");
            seed4.setCreatedBy(1l);
            seed4.setUpdatedBy(1l);
            seed4.setSpaceId(8l);

            ObjectSeederReq seed5 = new ObjectSeederReq();
            seed5.setName("TV");
            seed5.setDescription("Televisor 55'");
            seed5.setImage("LiveTvOutlinedIcon");
            seed5.setCreatedBy(1l);
            seed5.setUpdatedBy(1l);
            seed5.setSpaceId(8l);

            objectRepository.save(transformUtil.convertToObjectEntitySeeder(seed1));
            objectRepository.save(transformUtil.convertToObjectEntitySeeder(seed2));
            objectRepository.save(transformUtil.convertToObjectEntitySeeder(seed3));
            objectRepository.save(transformUtil.convertToObjectEntitySeeder(seed4));
            objectRepository.save(transformUtil.convertToObjectEntitySeeder(seed5));
        }catch (Exception e){
            logger.warning("Objects Seeding not required" + e);
        }
    }

    private void seedReportsTable(){
        try {
            ReportSeederReq seed1 = new ReportSeederReq();
            seed1.setName("Limpieza");
            seed1.setDescription("Jugo de mango regado en el piso");
            seed1.setTopicId(1L);
            seed1.setIsEvent(false);
            seed1.setTheDate(LocalDateTime.now());
            seed1.setSpaceId(6l);
            seed1.setCreatedBy(1l);
            seed1.setUpdatedBy(1l);
            seed1.setStatus(ReportStatus.PENDING);


            ReportSeederReq seed2 = new ReportSeederReq();
            seed2.setName("Reguero de café");
            seed2.setDescription("Requero de Café en el piso");
            seed2.setTopicId(1l);
            seed2.setIsEvent(false);
            seed2.setTheDate(LocalDateTime.now());
            seed2.setSpaceId(2l);
            seed2.setCreatedBy(1l);
            seed2.setUpdatedBy(1l);
            seed2.setStatus(ReportStatus.PENDING);

            ReportSeederReq seed3 = new ReportSeederReq();
            seed3.setName("PC sin MySql");
            seed3.setDescription("pc #10 sin funcionamiento correcto de MySql workbench");
            seed3.setTopicId(2l);
            seed3.setIsEvent(false);
            seed3.setTheDate(LocalDateTime.now());
            seed3.setSpaceId(4l);
            seed3.setCreatedBy(1l);
            seed3.setUpdatedBy(1l);
            seed3.setStatus(ReportStatus.COMPLETED);

            ReportSeederReq seed4 = new ReportSeederReq();
            seed4.setName("Conferencia Java");
            seed4.setDescription("Conferencia Java en Review 2");
            seed4.setTopicId(3l);
            seed4.setIsEvent(true);
            seed4.setTheDate(LocalDateTime.now());
            seed4.setSpaceId(8l);
            seed4.setCreatedBy(1l);
            seed4.setUpdatedBy(1l);
            seed4.setStatus(ReportStatus.CANCELLED);


            reportRepository.save(transformUtil.convertToReportEntitySeeder(seed1));
            reportRepository.save(transformUtil.convertToReportEntitySeeder(seed2));
            reportRepository.save(transformUtil.convertToReportEntitySeeder(seed3));
            reportRepository.save(transformUtil.convertToReportEntitySeeder(seed4));
        }catch (Exception e){
            logger.warning("Reports Seeding not required" + e);
        }
    }

    private void seedLostObjectsTable(){
        try {
            LostObjectSeederReq seed1 = new LostObjectSeederReq();
            seed1.setId(1l);
            seed1.setName("Audifonos inalambricos");
            seed1.setDescription("Audifonos inalambricos color negro");
            seed1.setImage("https://res.cloudinary.com/djmqgrcci/image/upload/v1727234276/fghzubrped4mqtikfgvn.jpg");
            seed1.setCreatedBy(1l);
            seed1.setUpdatedBy(1l);
            seed1.setStatus(LostObjectsStatus.PERDIDO);
            seed1.setSpaceId(3l);
            lostObjectsRepository.save(transformUtil.convertToLostObjectEntitySeeder(seed1));
        } catch (Exception e) {
            logger.warning("Objects Seeding not required" + e);
        }
    }
    @EventListener
    public void seed (ContextRefreshedEvent event){
        seedSpacesTable();
        seedObjectTable();
        seedReportsTable();
        seedLostObjectsTable();
    }
}
