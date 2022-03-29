package org.elsys.ufg;

public class Pipe extends MapObject {
    public Pipe(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "pipe-cross", 3);
        addType("pipe");
    }
}
