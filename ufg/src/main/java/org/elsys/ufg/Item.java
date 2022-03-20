package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.thymeleaf.standard.expression.GreaterThanExpression;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Item {
    @Id
    private String id;
    private String texture;
    private List<String> types;

    public Item(String texture) {
        this.texture = texture;
        this.types = new ArrayList<>();
        this.types.add("item");
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
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


}
