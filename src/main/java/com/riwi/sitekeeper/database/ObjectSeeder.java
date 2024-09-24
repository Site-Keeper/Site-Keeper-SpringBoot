package com.riwi.sitekeeper.database;

import com.riwi.sitekeeper.entities.ObjectEntity;
import com.riwi.sitekeeper.entities.SpaceEntity;
import com.riwi.sitekeeper.repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class ObjectSeeder {
    //Logger to see seed action
    public Logger logger = Logger.getLogger(String.valueOf(SpacesSeeder.class));
    //Object Repository to insert data into db
    public ObjectRepository objectRepository;

    @Autowired
    public ObjectSeeder (ObjectRepository objectRepository){
        this.objectRepository=objectRepository;
    }

    private void seedObjectTable(){
        try {
            ObjectEntity seed1 = new ObjectEntity();
            seed1.setId(6l);
            seed1.setName("Silla Ergonómica");
            seed1.setDescription("Silla egonómica marca MUMA");
            seed1.setImage("EventSeatOutlinedIcon");
            SpaceEntity space8 = new SpaceEntity();
            space8.setId(8l);
            seed1.setSpaceId(space8);
            ObjectEntity seed2 = new ObjectEntity();
            ObjectEntity seed3 = new ObjectEntity();
            ObjectEntity seed4 = new ObjectEntity();
            ObjectEntity seed5 = new ObjectEntity();
        }catch (Exception e){
            logger.warning("Objects Seeding not required");
        }
    }
}
