package org.elsys.ufg;

import java.util.ArrayList;
import java.util.List;

public class GameLogic implements Runnable {
    private boolean running;
    private Thread thread;
    private List<MapObject> mapObjects;


    GameLogic(){
        this.running = true;
        this.thread = new Thread(this);
        this.mapObjects = new ArrayList<>();
        this.thread.start();
    }

    @Override
    public void run() {
        while(running){
            System.out.println("Thread " + Thread.currentThread().getId());
            System.out.println(mapObjects.size());
            try {
                Thread.sleep(1000);
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
}
