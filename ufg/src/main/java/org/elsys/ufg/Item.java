package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Item extends GameObject {
    private String texture;

    public Item(String texture) {
        super();
        this.texture = texture;
        addType("item");
        setTicking(false);
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
