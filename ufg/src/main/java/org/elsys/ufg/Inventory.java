package org.elsys.ufg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, List<Slot>> inventories;
    private boolean hasInventory;

    public Inventory() {
        inventories = new HashMap<>();
        hasInventory = false;
    }

    public Map<String, List<Slot>> getInventories() {
        return inventories;
    }

    public void setInventories(Map<String, List<Slot>> inventories) {
        this.inventories = inventories;
    }

    public boolean isHasInventory() {
        return hasInventory;
    }

    public void setHasInventory(boolean hasInventory) {
        this.hasInventory = hasInventory;
    }

    public void addSlot(String inventoryType, Slot slot) {
        inventories.get(inventoryType).add(slot);
    }

    public List<Slot> getSlots(String inventoryType) {
        return inventories.get(inventoryType);
    }

    public void addInventory(String inventory) {
        inventories.put(inventory, new ArrayList<>());
        hasInventory = true;
    }

    public void addManyToInventory(String inventoryType, List<Item> items) {
        List<Slot> slots = inventories.get(inventoryType);

        for(Item item : items) {
            for (Slot slot : slots) {
                if(slot.isValidItemType(item.getClass())) {
                    slot.addItem(item);
                    break;
                }
            }
        }
    }

    public List<Item> getItemsFromInventory(Class <? extends Item> itemType, int amount) {
        List<Item> items = new ArrayList<>();
        List<Slot> Slots = inventories.get("output");

        for(Slot slot : Slots) {
            if(slot.isValidItemType(itemType)) {
                items.addAll(slot.getItems(amount));
            }
        }

        return items;
    }
}
