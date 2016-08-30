package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

/**
 * Created by leonardoleal on 29/08/16.
 */
public class Letter extends GameObject {

    private static final String TAG = "Letter";

    private Character value;
    private int originX;
    private int originY;

    public Letter(Character value, Bitmap imageResource) {
        this.value = value;
        setImageResource(imageResource);

        setWidth(imageResource.getWidth());
        setHeight(imageResource.getHeight());

        rectSrc = new Rect(0, 0, getWidth(), getHeight());
        rectDst = new Rect();

        updateDistortion();

        setX((int) ((GameUtil.SCREEN_WIDTH - getWidth()) * getRandPercent(0)));
        setY((int) ((GameUtil.SCREEN_HEIGHT - getHeight()) * getRandPercent(60)));
        originX = getX();
        originY = getY();
    }

    @Override
    public void update() {
        rectDst.left = getX();
        rectDst.right = getX() + getWidth();
        rectDst.top = getY();
        rectDst.bottom = getY() + getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImageResource(),
                rectSrc, rectDst, null);
    }

    public void startDrag(MotionEvent event) {
        setX((int) event.getX() - getWidth() / 2);
        setY((int) event.getY() - getHeight() / 2);

//        Log.d("ORIGIN X - Y", originX +" - "+ originY);
    }

    public void drop(boolean returnOrigin) {
        if (returnOrigin) {
            do {
                if (getX() > originX) setX(getX()-1);
                else if (getX() < originX) setX(getX()+1);

                if (getY() > originY) setY(getY()-1);
                else if (getY() < originY) setY(getY()+1);

            } while(originX != getX() || originY != getY());
        }
    }

    public Character getValue() {
        return value;
    }

    private double getRandPercent(int minValue) {
        Random rand = new Random();
        int max = 100 - minValue;

        return (rand.nextInt(max) + minValue) / 100.00;
    }
}