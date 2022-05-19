package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
public class BurnerDrill extends ExtractionMachine {
    public BurnerDrill(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "burner-drill", 3);
        inventory.getSlotsFromInventory("output").add(new Slot().setItemTypes(Arrays.asList(new Coal(), new RawIron(), new RawCopper())));
        addType("burnerDrill");
    }
}
