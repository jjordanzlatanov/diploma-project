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

    public List<Object> findMap(String username){
        if(mongoTemplate.getCollection(username).countDocuments() == 0){
            mongoTemplate.insert(mongoTemplate.findAll(Object.class, "initialMap"), username);
        }

        return mongoTemplate.find(new Query().addCriteria(Criteria.where("objectType").is("mapObject")).with(Sort.by(Sort.Direction.ASC, "priority")), Object.class, username);
    }

    public List<Object> findSelection(Integer startX, Integer startY, Integer endX, Integer endY, String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("objectType").is("mapObject").and("startX").lt(endX).and("startY").lt(endY).and("endX").gt(startX).and("endY").gt(startY)).with(Sort.by(Sort.Direction.ASC, "priority")), Object.class, username);
    }

    public boolean buildObject(Object object, String username) {
        MapObject mapObject = ((MapObject) object);
        List<Object> objects = findSelection(mapObject.getStartX(), mapObject.getStartY(), mapObject.getEndX(), mapObject.getEndY(), username);

        for(Object currentObject : objects){
            MapObject currentMapObject = ((MapObject) currentObject);

            if(currentMapObject.getPriority() >= mapObject.getPriority()){
                return false;
            }
        }

        save(object, username);
        return true;
    }
}


