package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RawIron extends Item {
    public RawIron() {
        super("iron-ore");
        this.addType("rawIron");
    }
}
