package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class MapEntity {
    @Id
    private String id;
    private Integer startX;
    private Integer startY;
    private Integer endY;
    private Integer endX;

    public MapEntity(Integer startX, Integer startY, Integer endY, Integer endX) {
        this.startX = startX;
        this.startY = startY;
        this.endY = endY;
        this.endX = endX;
    }
}
