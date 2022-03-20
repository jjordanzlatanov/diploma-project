package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Machine extends MapObject {
    private List<Item> inventory = new ArrayList<>();

    public Machine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
    }
}
