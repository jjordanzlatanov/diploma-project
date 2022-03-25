package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Machine extends MapObject {
    private List<Item> inventory = new ArrayList<>();

    public Machine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
        this.addType("machine");
        this.setTicking(true);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void addToInventory(Item item) {
        this.inventory.add(item);
    }

    public void addManyToInvetory(List<Item> items) {
        this.inventory.addAll(items);
    }
}
