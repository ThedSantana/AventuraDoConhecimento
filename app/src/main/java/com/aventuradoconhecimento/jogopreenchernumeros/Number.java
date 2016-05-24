package com.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Number extends GameObject {
    private int value;
    private boolean filled;
    private Paint paint;
    private Paint paintText;
    private int radius;

    public Number(Context context, int val, int xAxis, int yAxis, int color) {
        super(context);

        x = xAxis;
        y = yAxis;
        radius = 20;
        width = height = radius * 2;
        value = val;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(2);

        paintText = new Paint();
        paintText.setColor(color);
        paintText.setTextSize(24);
        paintText.setStrokeWidth(1);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);

        int xa = value < 10 ? 7 : 14;
//        String a= "cordX: "+ x + " cordY: "+ y;
//        canvas.drawText(Integer.toString(value)+a, x - xa, y + 8, paintText);
        canvas.drawText(Integer.toString(value), x - xa, y + 8, paintText);
    }

    public boolean isFilled()  {
        return filled;
    }

    public boolean isCorrect(int result) {
        return value == result;
    }

    public void fill() {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLUE);
        filled = true;
    }


    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    @Override
    public boolean isTouched(MotionEvent event) {
        if (event.getX() >= x-radius && event.getX() < (x + radius)
                && event.getY() >= y-radius && event.getY() < (y + radius)
                ) {
            return true;
        }

        return false;
    }
}