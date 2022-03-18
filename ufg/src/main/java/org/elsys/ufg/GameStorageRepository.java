package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameStorageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Object entity, String username){
        mongoTemplate.save(entity, username);
    }

    public List<Grass> findMap(String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("type").is("mapObject")), Grass.class, username);
    }
}


