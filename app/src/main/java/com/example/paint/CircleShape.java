package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CircleShape extends AreaShape {

    private int xEnd;
    private int yEnd;

    public CircleShape(int x, int y, String color) {
        super(x, y, color);
        xEnd = x;
        yEnd = y;
    }

    @Override
    public void updatePoint(int xe, int ye) {
        xEnd = xe;
        yEnd = ye;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas,paint);
        canvas.drawCircle(x,y,Math.abs(x-xEnd)/2,paint);
    }

    @Override
    public float getArea(){
        return (float)(((Math.abs(x-xEnd)/2)^2)*Math.PI);
    }
}