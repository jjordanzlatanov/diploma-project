package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Document
public abstract class RawMaterial extends MapObject {
    private Item item;
    private Integer gain;

    public RawMaterial(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority, Item item, Integer gain) {
        super(startX, startY, endX, endY, texture, priority);
        this.item = item;
        this.gain = gain;
        this.addType("rawMaterial");
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getGain() {
        return gain;
    }

    public void setGain(Integer gain) {
        this.gain = gain;
    }

    public List<Item> getYield() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Item> yield = new ArrayList<>();

        for(int i = 0; i < gain; i++){
            yield.add(this.item.getClass().getConstructor().newInstance());
        }

        return yield;
    }
}
