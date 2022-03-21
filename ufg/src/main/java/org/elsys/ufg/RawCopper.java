package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RawCopper extends Item {
    public RawCopper() {
        super("copper-ore");
        this.addType("rawCopper");
    }
}
