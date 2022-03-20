package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CopperOre extends RawMaterial {
    public CopperOre(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "copper-ore", 1, "rawCopper", 2);
        this.addType("copperOre");
    }
}
