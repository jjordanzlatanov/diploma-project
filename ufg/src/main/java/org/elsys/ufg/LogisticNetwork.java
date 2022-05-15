package org.elsys.ufg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LogisticNetwork extends GameObject {
    private List<Inventory> inputs;
    private List<Inventory> outputs;

    public LogisticNetwork() {
        super();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        addType("logisticNetwork");
    }

    @Override
    public void tick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(Inventory input : inputs) {
            int amount = 4;

            for(Inventory output : outputs) {
                List<Item> items = output.getItemsFromInventory("output", input.getRequiredItem(), amount);
                input.addManyToInventory("input", items);

                amount -= items.size();
                if(amount == 0) {
                    break;
                }
            }
        }
    }
}
