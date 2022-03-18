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
    private String type;

    public MapEntity(Integer startX, Integer startY, Integer endY, Integer endX) {
        this.startX = startX;
        this.startY = startY;
        this.endY = endY;
        this.endX = endX;
        this.type = "mapObject";
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public Integer getEndY() {
        return endY;
    }

    public void setEndY(Integer endY) {
        this.endY = endY;
    }

    public Integer getEndX() {
        return endX;
    }

    public void setEndX(Integer endX) {
        this.endX = endX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
