package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Bitmap image;
    protected Context context;

    public GameObject(Context c) {
        context = c;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public boolean isTouched(MotionEvent event) {
        if (event.getX() >= x && event.getX() < (x + width)
                && event.getY() >= y && event.getY() < (y + height)
        ) {
            return true;
        }

        return false;
    }
}