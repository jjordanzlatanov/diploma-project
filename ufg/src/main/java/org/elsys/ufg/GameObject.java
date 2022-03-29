package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Document
public abstract class GameObject {
    @Id
    private String id;
    private List<String> types;
    private boolean ticking;
    private UUID uuid;
    private Map<String, List<Item>> inventories;
    private boolean hasInventory;

    public GameObject() {
        types = new ArrayList<>();
        uuid = UUID.randomUUID();
        addType("gameObject");
        inventories = new HashMap<>();
        hasInventory = false;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void addType(String type){
        types.add(type);
    }

    public String getType(){
        return types.get(types.size() - 1);
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public void tick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Map<String, List<Item>> getInventories() {
        return inventories;
    }

    public void setInventories(Map<String, List<Item>> inventories) {
        this.inventories = inventories;
    }

    public void addInventory(String inventory) {
        inventories.put(inventory, new ArrayList<>());
        hasInventory = true;
    }

    public void addToInventory(String inventory, Item item) {
        inventories.get(inventory).add(item);
    }

    public void addManyToInventory(String inventory, List<Item> items) {
        inventories.get(inventory).addAll(items);
    }
}
