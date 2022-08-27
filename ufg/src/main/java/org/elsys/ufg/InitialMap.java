package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitialMap {
    @Autowired
    private GameStorageRepository gameStorageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void load(){
        if(!mongoTemplate.collectionExists("initialMap")){
            loadMap();
        }
    }

    public void loadMap(){
        for(int x = 0; x < 2010; x += 30){
            for(int y = 0; y < 1100; y += 30){
                gameStorageRepository.save(new Grass(x, y, x + 30, y + 30), "initialMap");
            }
        }

        for(int x = 0; x <= 420; x += 30){
            for(int y = 0; y <= 120; y += 30){
                gameStorageRepository.save(new CoalOre(x, y, x + 30, y + 30), "initialMap");
            }
        }

        for(int x = 600; x <= 1110; x += 30){
            for(int y = 0; y <= 120; y += 30){
                gameStorageRepository.save(new IronOre(x, y, x + 30, y + 30), "initialMap");
            }
        }

        for(int x = 1230; x <= 2010; x += 30){
            for(int y = 0; y <= 120; y += 30){
                gameStorageRepository.save(new CopperOre(x, y, x + 30, y + 30), "initialMap");
            }
        }
    }
}
