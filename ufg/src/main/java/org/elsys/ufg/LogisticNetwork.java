package org.elsys.ufg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LogisticNetwork extends GameObject {
    private List<Slot> inputs;
    private List<Slot> outputs;

    public LogisticNetwork() {
        super();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        setTicking(true);
        addType("logisticNetwork");
    }

    @Override
    public void tick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(Slot input : inputs) {
            int amount = 4;

            for(Slot output : outputs) {
                if(input.areValidItemTypes(output.getItemTypes())){
                    List<Item> items = output.getItems(amount);
                    input.addItems(items);

                    amount -= items.size();

                    if(amount == 0) {
                        break;
                    }
                }
            }
        }
    }
}
