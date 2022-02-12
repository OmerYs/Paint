package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PathShape extends Shape {

    private int xEnd;
    private int yEnd;
    private Path p = new Path();

    public PathShape(int x, int y, String color) {
        super(x, y, color);
        xEnd = x;
        yEnd = y;
        p.moveTo(x,y);
    }

    @Override
    public void updatePoint(int xe, int ye) {
        xEnd = xe;
        yEnd = ye;
        p.lineTo(xEnd,yEnd);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas,paint);
        canvas.drawPath(p,paint);
    }
}