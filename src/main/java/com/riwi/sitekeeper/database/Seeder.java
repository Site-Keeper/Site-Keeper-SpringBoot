package com.riwi.sitekeeper.database;

import com.riwi.sitekeeper.dtos.requests.ObjectReq;
import com.riwi.sitekeeper.entities.SpaceEntity;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import com.riwi.sitekeeper.repositories.SpaceRepository;
import com.riwi.sitekeeper.utils.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Seeder {
    //Logger to see seed action
    public Logger logger = Logger.getLogger(String.valueOf(Seeder.class));
    //Spaces Repository to insert data into db
    public SpaceRepository spaceRepository;
    //Object Repository to insert data into db
    public ObjectRepository objectRepository;
    public TransformUtil transformUtil;
    @Autowired
    public Seeder (
            SpaceRepository spaceRepository,
            ObjectRepository objectRepository,
            TransformUtil transformUtil){
        this.spaceRepository = spaceRepository;
        this.objectRepository = objectRepository;
        this.transformUtil = transformUtil;
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
            ObjectReq seed1 = new ObjectReq();
            seed1.setName("Silla Ergonómica");
            seed1.setDescription("Silla egonómica marca MUMA");
            seed1.setImage("EventSeatOutlinedIcon");
            seed1.setSpaceId(7l);

            ObjectReq seed2 = new ObjectReq();
            seed2.setName("PC");
            seed2.setDescription("PC marca Compumax");
            seed2.setImage("ComputerOutlinedIcon");
            seed2.setSpaceId(8l);

            ObjectReq seed3 = new ObjectReq();
            seed3.setName("Silla Ergonómica");
            seed3.setDescription("Silla egonómica marca MUMA");
            seed3.setImage("EventSeatOutlinedIcon");
            seed3.setSpaceId(8l);

            ObjectReq seed4 = new ObjectReq();
            seed4.setName("PC");
            seed4.setDescription("PC marca Compumax");
            seed4.setImage("ComputerOutlinedIcon");
            seed4.setSpaceId(8l);

            ObjectReq seed5 = new ObjectReq();
            seed5.setName("TV");
            seed5.setDescription("Televisor 55'");
            seed5.setImage("LiveTvOutlinedIcon");
            seed5.setSpaceId(8l);

            objectRepository.save(transformUtil.convertToObjectEntity(seed1));
            objectRepository.save(transformUtil.convertToObjectEntity(seed2));
            objectRepository.save(transformUtil.convertToObjectEntity(seed3));
            objectRepository.save(transformUtil.convertToObjectEntity(seed4));
            objectRepository.save(transformUtil.convertToObjectEntity(seed5));
        }catch (Exception e){
            logger.warning("Objects Seeding not required");
        }
    }

    @EventListener
    public void seed (ContextRefreshedEvent event){
        seedSpacesTable();
        seedObjectTable();
    }
}
