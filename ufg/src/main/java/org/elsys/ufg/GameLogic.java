package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameLogic implements Runnable {
    private List<GameObject> gameObjects;
    private String username;
    private boolean running;
    private Thread thread;

    GameLogic(String username){
        this.gameObjects = new ArrayList<>();
        this.username = username;
        this.running = true;
        this.thread = new Thread(this);
        this.thread.start();
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
        this.running = false;
    }

    public void loadGame(List<GameObject> gameObjects){
        this.gameObjects.addAll(gameObjects);
    }

    public void addGameObject(GameObject gameObject){
        this.gameObjects.add(gameObject);
    }

    public List<GameObject> getGameObjects(){
        return this.gameObjects;
    }
}
