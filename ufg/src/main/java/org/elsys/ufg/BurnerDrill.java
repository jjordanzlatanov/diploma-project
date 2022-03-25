package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BurnerDrill extends ExtractionMachine {
    public BurnerDrill(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "burner-drill", 3);
        addType("burnerDrill");
    }
}
