package org.elsys.ufg;

import java.util.ArrayList;
import java.util.List;

public class Slot {
    private List<? extends Item> itemTypes;
    private List<Item> items;
    private int limit;

    public Slot() {
        items = new ArrayList<>();
        itemTypes = new ArrayList<>();
        limit = 100;
    }

    public List<? extends Item> getItemTypes() {
        return itemTypes;
    }

    public Slot setItemTypes(List<? extends Item> itemTypes) {
        this.itemTypes = itemTypes;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isValidItemType(Class<? extends Item> itemType) {
        for(Item item : itemTypes) {
            if(item.getClass().equals(itemType)) {
                return true;
            }
        }

        return false;
    }

    public boolean areValidItemTypes(List <? extends Item> newItemTypes) {
        for(Item item : itemTypes) {
            for(Item newItem : newItemTypes) {
                if(item.getClass().equals(newItem.getClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addItems(List <Item> newItems) {
        int availableSpaces = limit - items.size();

        if(availableSpaces >= newItems.size()) {
            items.addAll(newItems);
            return;
        }

        for (Item item : newItems) {
            if(availableSpaces == 0) {
                break;
            }else{
                availableSpaces--;
            }

            items.add(item);
        }
    }

    public void addItem(Item item) {
        int availableSpaces = limit - items.size();

        if(availableSpaces >= 1) {
            items.add(item);
        }
    }

    public List<Item> getItems(int amount) {
        List<Item> returnedItems = new ArrayList<>();

        int amountItems = items.size();

        for(Item item : items) {
            if(amountItems == 0) {
                break;
            }else{
                amountItems--;
            }

            returnedItems.add(item);
        }

        items.removeAll(returnedItems);

        return returnedItems;
    }
}
