package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Document
public abstract class MapObject extends GameObject {
    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;
    private String texture;
    private Integer priority;

    public MapObject(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.texture = texture;
        this.priority = priority;
        addType("mapObject");
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

    public Integer getEndX() {
        return endX;
    }

    public void setEndX(Integer endX) {
        this.endX = endX;
    }

    public Integer getEndY() {
        return endY;
    }

    public void setEndY(Integer endY) {
        this.endY = endY;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void tick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}
}
