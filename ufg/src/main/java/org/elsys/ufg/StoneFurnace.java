package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

// MAKE SMELTING MACHINE ABSTRACT CLASS!!!

@Document
public class StoneFurnace extends Machine {
    public StoneFurnace(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "stone-furnace", 3);
    }
}
