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
        game.put(username, new GameLogic(username));
    }

    public void stopGame(String username){
        game.get(username).stop();
        game.remove(username);
    }

    public void loadGame(List<GameObject> gameObjects, String username){
        this.game.get(username).loadGame(gameObjects);
    }

    public void addGameObject(GameObject gameObject, String username){
        this.game.get(username).addGameObject(gameObject);
    }

    public List<GameObject> getGameObjects(String username){
        return this.game.get(username).getGameObjects();
    }
}
