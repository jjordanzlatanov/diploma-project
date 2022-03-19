package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class RawMaterial extends MapObject {
    private String material;
    private Integer gain;

    public RawMaterial(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority, String material, Integer gain) {
        super(startX, startY, endX, endY, texture, priority);
        this.material = material;
        this.gain = gain;
    }
}
