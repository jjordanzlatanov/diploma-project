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
    private GameStorageRepository gameStorageRepository;

    GameLogic(String username, GameStorageRepository gameStorageRepository){
        this.username = username;
        this.gameStorageRepository = gameStorageRepository;
        gameObjects = new ArrayList<>();
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(running){
            synchronized (gameObjects){
                for(GameObject gameObject : gameObjects){
                    try {
                        gameObject.tick();
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
        synchronized (gameObjects) {
            running = false;
        }
    }

    public void loadGame(List<GameObject> gameObjects){
        synchronized (gameObjects) {
            this.gameObjects.addAll(gameObjects);
        }
    }

    public void addGameObject(GameObject gameObject){
        synchronized (gameObjects) {
            gameObjects.add(gameObject);
        }
    }

    public List<GameObject> getGameObjects(){
        synchronized (gameObjects) {
            return gameObjects;
        }
    }

    public void updateGame(){
        synchronized (gameObjects) {
            for(GameObject gameObject : gameObjects) {
                gameStorageRepository.save(gameObject, username);
            }
        }
    }

    public void deleteGameObject(GameObject gameObject) {
        synchronized (gameObjects) {
            gameObjects.removeIf(currentGameObject -> currentGameObject.getUuid().equals(gameObject.getUuid()));
        }
    }
}
