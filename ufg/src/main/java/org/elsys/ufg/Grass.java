package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Grass extends MapEntity {
    private String texture;
    private Integer priority;

    public Grass(Integer startX, Integer startY, Integer endY, Integer endX, String texture) {
        super(startX, startY, endY, endX);
        this.texture = texture;
        this.priority = 0;
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
}
