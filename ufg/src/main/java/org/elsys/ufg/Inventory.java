package org.elsys.ufg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, List<Item>> inventories;
    private boolean hasInventory;
    private Class<? extends Item> requiredItem;

    public Inventory() {
        inventories = new HashMap<>();
        hasInventory = false;
    }

    public void addInventory(String inventory) {
        inventories.put(inventory, new ArrayList<>());
        hasInventory = true;
    }

    public Class<? extends Item> getRequiredItem() {
        return requiredItem;
    }

    public void setRequiredItem(Class<? extends Item> requiredItem) {
        this.requiredItem = requiredItem;
    }

    public void addManyToInventory(String inventory, List<Item> items) {
        inventories.get(inventory).addAll(items);
    }

    public List<Item> getItemsFromInventory(String inventoryType, Class <? extends Item> item, Integer amount) {
        List<Item> items = new ArrayList<>();
        List<Item> inventory = inventories.get(inventoryType);

        for(Item currentItem : inventory) {
            if(currentItem.getClass().equals(item)) {
                items.add(currentItem);

                if(items.size() == amount) {
                    break;
                }
            }
        }

        inventory.removeAll(items);
        return items;
    }
}
