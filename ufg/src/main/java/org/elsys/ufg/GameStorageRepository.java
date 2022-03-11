package org.elsys.ufg;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameStorageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(MapEntity entity, String user){
        mongoTemplate.save(entity, user);
    }

    public List<? extends MapEntity> findAll(Class<? extends MapEntity> entityClass, String user){
        return mongoTemplate.find(new Query().restrict(entityClass), entityClass, user);
    }
}
