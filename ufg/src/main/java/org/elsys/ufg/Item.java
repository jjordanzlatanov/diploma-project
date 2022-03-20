package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Item {
    @Id
    private String id;
    private String texture;
    private String objectType;

    public Item(String texture) {
        this.texture = texture;
        this.objectType = "itemObject";
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
