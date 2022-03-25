package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Machine extends MapObject {
    private List<Item> inventory = new ArrayList<>();

    public Machine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
        addType("machine");
        setTicking(true);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void addManyToInventory(List<Item> items) {
        inventory.addAll(items);
    }
}
