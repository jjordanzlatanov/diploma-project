package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class ExtractionMachine extends Machine {
    private String material;

    public ExtractionMachine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
        this.addType("extractionMachine");
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
