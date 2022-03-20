package org.elsys.ufg;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Machine extends MapObject {
    public Machine(Integer startX, Integer startY, Integer endX, Integer endY, String texture, Integer priority) {
        super(startX, startY, endX, endY, texture, priority);
    }
}
