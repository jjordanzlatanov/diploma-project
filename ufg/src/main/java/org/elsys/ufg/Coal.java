package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Coal extends Item {
    public Coal() {
        super("coal");
        addType("coal");
    }
}
