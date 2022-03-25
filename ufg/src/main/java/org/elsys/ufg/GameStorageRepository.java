package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class GameStorageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Object object, String username){
        mongoTemplate.save(object, username);
    }

    public List<MapObject> findMap(String username){
        if(mongoTemplate.getCollection(username).countDocuments() == 0){
            mongoTemplate.insert(mongoTemplate.findAll(Object.class, "initialMap"), username);
        }

        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("mapObject")).with(Sort.by(Sort.Direction.ASC, "priority")), MapObject.class, username);
    }

    public List<GameObject> findGame(String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("ticking").is(true)), GameObject.class, username);
    }

    public boolean buildObject(MapObject mapObject, String username) {
        MapObject intersectingObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("startX").lt(mapObject.getEndX()).and("startY").lt(mapObject.getEndY()).and("endX").gt(mapObject.getStartX()).and("endY").gt(mapObject.getStartY()).and("priority").gte(mapObject.getPriority())), MapObject.class, username);

        if(intersectingObject != null) {
            return false;
        }

        if(mapObject.getTypes().contains("extractionMachine")){
            ExtractionMachine extractionMachine = ((ExtractionMachine) mapObject);
            List<RawMaterial> resources = findResources(extractionMachine, username);

            if(resources.size() != 0){
                extractionMachine.setMaterial(resources.get(0));
                extractionMachine.setAmountTiles(resources.size());
            }
        }

        save(mapObject, username);
        return true;
    }

    public List<RawMaterial> findResources(ExtractionMachine extractionMachine, String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("rawMaterial").and("startX").gte(extractionMachine.getStartX()).lt(extractionMachine.getEndX()).and("startY").gte(extractionMachine.getStartY()).lt(extractionMachine.getEndY()).and("endX").gt(extractionMachine.getStartX()).lte(extractionMachine.getEndX()).and("endY").gt(extractionMachine.getStartY()).lte(extractionMachine.getEndY())), RawMaterial.class, username);
    }

    public void updateObject(GameObject gameObject, String username){
        mongoTemplate.save(gameObject, username);
    }
}


