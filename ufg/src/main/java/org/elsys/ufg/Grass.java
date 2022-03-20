package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Grass extends MapObject {
    public Grass(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "grass", 0);
        this.addType("grass");
    }
}
