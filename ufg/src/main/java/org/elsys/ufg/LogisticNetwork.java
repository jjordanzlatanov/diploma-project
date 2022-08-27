package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Document
public class LogisticNetwork extends GameObject {
    private List<Slot> inputs;
    private List<Slot> outputs;
    private List<Pipe> pipes;

    public LogisticNetwork() {
        super();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        pipes = new ArrayList<>();
        setTicking(true);
        addType("logisticNetwork");
    }

    public void addInventory(Inventory inventory) {
        inputs.addAll(inventory.getSlots("input"));
        outputs.addAll(inventory.getSlots("output"));
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public void addPipes(List <Pipe> newPipes) {
        pipes.addAll(newPipes);
    }

    public List<Slot> getInputs() {
        return inputs;
    }

    public void addInputs(List<Slot> newInputs) {
        inputs.addAll(newInputs);
    }

    public List<Slot> getOutputs() {
        return outputs;
    }

    public void addOutputs(List<Slot> newOutputs) {
        outputs.addAll(newOutputs);
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
