package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class PaintView extends View {

    private Paint bgPaint;
    private String currentShape = "Rect";
    private String currentColor = "#FFFFFFFF";

    protected Stack<Shape> shapes;
    protected Stack<Paint> paints;

    public PaintView(Context context) {
        super(context);
        shapes = new Stack<>();
        paints = new Stack<>();
        bgPaint = new Paint();
        bgPaint.setColor(Color.rgb(255,255,255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(bgPaint);
        for (int i = 0; i < shapes.size(); i++)
            shapes.get(i).draw(canvas, paints.get(i));
    }

    Shape shape;
    Paint paint;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(currentShape.equals("Rect"))
                shape = new RectShape((int) event.getX(), (int) event.getY(), currentColor);
            else if(currentShape.equals("Circle"))
                shape = new CircleShape((int) event.getX(), (int) event.getY(), currentColor);
            else if(currentShape.equals("Line"))
                shape = new LineShape((int) event.getX(), (int) event.getY(), currentColor);
            else
                shape = new PathShape((int) event.getX(), (int) event.getY(), currentColor);
            shapes.push(shape);

            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);
            paints.push(paint);
            invalidate();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            shape.updatePoint((int)event.getX(),(int)event.getY());
            invalidate();
        }
        return true;
    }

    public void addLine() {
        currentShape = "Line";
    }

    public void addRect() {
        currentShape = "Rect";
    }

    public void addCircle() {
        currentShape = "Circle";
    }
    public void addPath() {
        currentShape = "Path";
    }

    public void setColor(String color)
    {
        currentColor = color;
    }

    public void undo()
    {
        if(!shapes.isEmpty()) {
            shapes.pop();
            paints.pop();
            invalidate();
        }
    }
    public void undoPaths(){
        Stack<Shape> tempS = new Stack<Shape>();
        Stack<Paint> tempP = new Stack<Paint>();
        while(shapes.isEmpty() == false) {
            if (!(shapes.peek() instanceof PathShape)) {
                tempS.add(shapes.pop());
                tempP.add(paints.pop());
            }
            else{
                shapes.pop();
                paints.pop();
            }
        }
        while(!tempS.isEmpty()){
            shapes.add(tempS.pop());
            paints.add(tempP.pop());
        }
        invalidate();
    }

    public void fillStyle(boolean fill){
        if(fill)
            paints.peek().setStyle(Paint.Style.FILL_AND_STROKE);
        else
            paints.peek().setStyle(Paint.Style.STROKE);
        invalidate();
    }

    public void setStrokeWidth(boolean a){
        float p = paints.peek().getStrokeWidth();
        if(a)
            p += 1;
        else
            p -= 1;
        paints.peek().setStrokeWidth(p);
        invalidate();
    }

    public boolean keepBiggest(){
        Stack<AreaShape> tempS = new Stack<AreaShape>();
        Stack<Paint> tempP = new Stack<Paint>();
        float max = 0, a = 0;
        while(shapes.isEmpty() == false) {
            if (shapes.peek() instanceof AreaShape) {
                tempS.add((AreaShape)shapes.pop());
                tempP.add(paints.pop());
                a = tempS.peek().getArea();
                if(a > max)
                    max = a;
            }
            else{
                shapes.pop();
                paints.pop();
            }
        }
        while(!tempS.isEmpty()){
            if(tempS.peek().getArea() == max) {
                shapes.add(tempS.pop());
                paints.add(tempP.pop());
            }
            else{
                tempS.pop();
                tempP.pop();
            }
        }
        invalidate();
        if(shapes.isEmpty()){
            return false;
        }
        return true;
    }
}
