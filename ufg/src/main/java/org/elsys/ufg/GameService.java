package org.elsys.ufg;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameService {
    private Map<String, GameLogic> game;

    GameService(){
        game = new ConcurrentHashMap<>();
    }

    public void addGame(String username){
        game.put(username, new GameLogic(username));
    }

    public void stopGame(String username){
        game.get(username).stop();
        game.remove(username);
    }

    public void loadGame(List<GameObject> gameObjects, String username) {
        List<GameObject> tickingGameObjects = gameObjects.stream().filter(GameObject::isTicking).toList();
        game.get(username).loadGame(tickingGameObjects);
    }

    public void addGameObject(GameObject gameObject, String username){
        game.get(username).addGameObject(gameObject);
    }

    public List<GameObject> getGameObjects(String username){
        if(game.get(username) == null){
            return new ArrayList<>();
        }

        return game.get(username).getGameObjects();
    }
}
