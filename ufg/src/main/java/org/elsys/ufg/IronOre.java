package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IronOre extends RawMaterial {
    public IronOre(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "iron-ore", 1, new RawIron(), 5);
        addType("ironOre");
    }
}
