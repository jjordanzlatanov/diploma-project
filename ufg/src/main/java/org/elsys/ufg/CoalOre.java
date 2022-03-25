package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CoalOre extends RawMaterial {
    public CoalOre(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "coal", 1, new Coal(), 3);
        addType("coalOre");
    }
}
