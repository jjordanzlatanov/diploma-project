package org.elsys.ufg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, List<Item>> inventories;
    private boolean hasInventory;

    public Inventory() {
        inventories = new HashMap<>();
        hasInventory = false;
    }

    public void addInventory(String inventory) {
        inventories.put(inventory, new ArrayList<>());
        hasInventory = true;
    }

    public void addManyToInventory(String inventory, List<Item> items) {
        inventories.get(inventory).addAll(items);
    }
}
