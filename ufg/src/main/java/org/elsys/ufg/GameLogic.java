package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameLogic implements Runnable {
    private String username;
    private List<GameObject> gameObjects;
    private boolean running;
    private Thread thread;

    GameLogic(String username){
        this.username = username;
        gameObjects = new ArrayList<>();
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(running){
            for(GameObject gameObject : gameObjects){
                try {
                    gameObject.tick();
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        running = false;
    }

    public void loadGame(List<GameObject> gameObjects){
        this.gameObjects.addAll(gameObjects);
    }

    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }
}
