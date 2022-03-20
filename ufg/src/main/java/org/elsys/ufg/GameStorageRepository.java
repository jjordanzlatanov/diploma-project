package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

        return mongoTemplate.find(new Query().addCriteria(Criteria.where("objectType").is("mapObject")).with(Sort.by(Sort.Direction.ASC, "priority")), MapObject.class, username);
    }

    public boolean buildObject(MapObject mapObject, String username) {
        MapObject intersectingObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("objectType").is("mapObject").and("startX").lt(mapObject.getEndX()).and("startY").lt(mapObject.getEndY()).and("endX").gt(mapObject.getStartX()).and("endY").gt(mapObject.getStartY()).and("priority").gte(mapObject.getPriority())), MapObject.class, username);

        if(intersectingObject != null) {
            return false;
        }

        save(mapObject, username);
        return true;
    }
}


