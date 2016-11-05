package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

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
        radius = GameView.getWidthProportion(20);
        width = height = radius * 2;
        value = val;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(2);

        paintText = new Paint();
        paintText.setColor(color);
        paintText.setTextSize(GameView.getWidthProportion(24));
        paintText.setStrokeWidth(1);
    }

    public Number(int value) {
        super(null);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);

        int xa = GameView.getHeightProportion(value < 10 ? 7 : 14);
        int ya = GameView.getHeightProportion(8);

        canvas.drawText(Integer.toString(value), x - xa, y + ya, paintText);
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
        return event.getX() >= x - radius && event.getX() < (x + radius)
                && event.getY() >= y - radius && event.getY() < (y + radius);

    }

    @Override
    public boolean equals(Object o) {
        return getValue() == ((Number) o).getValue();
    }
}