package org.elsys.ufg;

public class IronChest extends MapObject {
    public IronChest(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "iron-chest", 3);
        inventory.addInventory("inventory");
        addType("ironChest");
    }
}
