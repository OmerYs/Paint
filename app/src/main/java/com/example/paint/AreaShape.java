package com.example.paint;

public abstract class AreaShape extends Shape{
    public AreaShape(int x, int y, String color) {
        super(x, y, color);
    }

    public float getArea(){
        return 0;
    }
}
