package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;

@Document
public abstract class ExtractionMachine extends Machine {
    private RawMaterial material;
    private Integer amountTiles;

    public ExtractionMachine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
        this.addType("extractionMachine");
    }

    public RawMaterial getMaterial() {
        return material;
    }

    public void setMaterial(RawMaterial material) {
        this.material = material;
    }

    public Integer getAmountTiles() {
        return amountTiles;
    }

    public void setAmountTiles(Integer amountTiles) {
        this.amountTiles = amountTiles;
    }

    public void extract() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(material != null){
            for(int i = 0; i < amountTiles; i++){
                this.addManyToInvetory(material.getYield());
            }
        }
    }
}
