package org.elsys.ufg;

public class Mouse {
    private Integer x;
    private Integer y;

    public Mouse(){}

    public Mouse(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getRoundX(){
        Integer rounded = getX() / 30;
        return rounded * 30;
    }

    public Integer getRoundY(){
        Integer rounded = getY() / 30;
        return rounded * 30;
    }
}
