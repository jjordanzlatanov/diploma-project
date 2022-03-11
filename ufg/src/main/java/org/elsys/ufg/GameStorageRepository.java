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

    public void save(Tile tile){
        mongoTemplate.save(tile, "chunks");
    }

    public Tile find(){
        return mongoTemplate.find(new Query(), Tile.class, "chunks").get(0);
    }

    public List<Tile> findTiles(){
        return mongoTemplate.find(new Query().restrict(Tile.class), Tile.class, "chunks");
    }
}
