package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameLogic implements Runnable {
    private boolean running;
    private Thread thread;
    private List<MapObject> mapObjects;
    private String username;

    GameLogic(String username){
        this.running = true;
        this.thread = new Thread(this);
        this.mapObjects = new ArrayList<>();
        this.thread.start();
        this.username = username;
    }

    @Override
    public void run() {
        while(running){
            for(MapObject mapObject : mapObjects){
                try {
                    mapObject.tick();
                    if(mapObject instanceof ExtractionMachine){
                        System.out.println(username);
                        // updateObject(mapObject, username, 10);
                    }
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

    public void loadMap(List<MapObject> mapObjects){
        this.mapObjects.addAll(mapObjects);
    }

    public void addMapObject(MapObject mapObject){
        this.mapObjects.add(mapObject);
    }

    public List<MapObject> getMap(){
        return mapObjects;
    }
}
