package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Repository
public class GameStorageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CreateService createService;

    public void save(GameObject gameObject, String username){
        mongoTemplate.save(gameObject, username);
    }

    public List<GameObject> findGame(String username){
        if(!mongoTemplate.collectionExists(username)){
            mongoTemplate.insert(mongoTemplate.findAll(GameObject.class, "initialMap"), username);
        }

        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("mapObject")).with(Sort.by(Sort.Direction.ASC, "priority")), GameObject.class, username);
    }

    public GameObject buildObject(Action action, String username) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String objectType = action.getObjectType();

        if(objectType == null){
            return null;
        }

        Pattern pattern = createService.getPattern(objectType);

        int startX = action.getStartX();
        int startY = action.getStartY();
        int endX = startX + pattern.getWidth();
        int endY = startY + pattern.getHeight();
        int priority = pattern.getPriority();

        GameObject intersectingObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("startX").lt(endX).and("startY").lt(endY).and("endX").gt(startX).and("endY").gt(startY).and("priority").gte(priority)), GameObject.class, username);

        if(intersectingObject != null) {
            return null;
        }

        GameObject gameObject = createService.create(objectType, startX, startY, endX, endY);

        if(gameObject.getTypes().contains("extractionMachine")){
            ExtractionMachine extractionMachine = ((ExtractionMachine) gameObject);
            List<RawMaterial> resources = findResources(extractionMachine, username);

            if(resources.size() != 0){
                extractionMachine.setMaterial(resources.get(0));
                extractionMachine.setAmountTiles(resources.size());
            }
        }

        save(gameObject, username);
        return gameObject;
    }

    public List<RawMaterial> findResources(ExtractionMachine extractionMachine, String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("rawMaterial").and("startX").gte(extractionMachine.getStartX()).lt(extractionMachine.getEndX()).and("startY").gte(extractionMachine.getStartY()).lt(extractionMachine.getEndY()).and("endX").gt(extractionMachine.getStartX()).lte(extractionMachine.getEndX()).and("endY").gt(extractionMachine.getStartY()).lte(extractionMachine.getEndY())), RawMaterial.class, username);
    }

    public void updateObject(GameObject gameObject, String username){
        mongoTemplate.save(gameObject, username);
    }
}


