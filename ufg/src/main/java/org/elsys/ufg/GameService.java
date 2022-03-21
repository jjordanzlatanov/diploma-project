package org.elsys.ufg;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private Map<String, GameLogic> game;

    GameService(){
        this.game = new ConcurrentHashMap<>();
    }

    public void addGame(String username){
        game.put(username, new GameLogic());
    }

    public void stopGame(String username){
        game.get(username).stop();
        game.remove(username);
    }

    public void loadMap(List<MapObject> mapObjects, String username){
        this.game.get(username).loadMap(mapObjects);
    }

    public void addMapObject(MapObject mapObject, String username){
        this.game.get(username).addMapObject(mapObject);
    }
}
