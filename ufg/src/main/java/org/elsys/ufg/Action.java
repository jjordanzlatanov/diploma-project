package org.elsys.ufg;

public class Action {
    private int x;
    private int y;
    private String objectType;

    public Action(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getRoundX(){
        int roundX = x / 30;
        return roundX * 30;
    }

    public int getRoundY(){
        int roundY = y / 30;
        return roundY * 30;
    }

    @Override
    public String toString() {
        return "Action{" +
                "x=" + x +
                ", y=" + y +
                ", objectType='" + objectType + '\'' +
                '}';
    }
}
