package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Document
public abstract class GameObject {
    @Id
    private String id;
    private List<String> types;
    private boolean ticking;

    public GameObject(){
        types = new ArrayList<>();
        addType("gameObject");
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void addType(String type){
        this.types.add(type);
    }

    public String getType(){
        return this.types.get(this.types.size() - 1);
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public void tick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}
}
