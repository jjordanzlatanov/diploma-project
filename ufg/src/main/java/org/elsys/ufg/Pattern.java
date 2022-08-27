package org.elsys.ufg;

public class Pattern {
    private Class<? extends GameObject> patternClass;
    private int width;
    private int height;
    private int priority;

    public Pattern(Class<? extends GameObject> patternClass, int width, int height, int priority) {
        this.patternClass = patternClass;
        this.width = width;
        this.height = height;
        this.priority = priority;
    }

    public Class<? extends GameObject> getPatternClass() {
        return patternClass;
    }

    public void setPatternClass(Class<? extends GameObject> patternClass) {
        this.patternClass = patternClass;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
