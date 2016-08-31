package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

public class Figure extends GameObject {

    private static final String TAG = "Figure";

    private String name;
    private int score;

    public Figure(String name, Bitmap imageResource) {
        setImageResource(imageResource);
        setName(name);
        setScore(name.length());
        setWidth(imageResource.getWidth());
        setHeight(imageResource.getHeight());

        rectSrc = new Rect(0, 0, getWidth(), getHeight());
        rectDst = new Rect();

        super.updateDistortion();
    }

    @Override
    public void update() {
        if (UPDATE) {
            setX((GameUtil.SCREEN_WIDTH - getWidth()) / 2);
            setY((int) ((GameUtil.SCREEN_HEIGHT - getHeight()) * 0.15));

            rectDst.left = getX();
            rectDst.right = getX() + getWidth();
            rectDst.top = getY();
            rectDst.bottom = getY() + getHeight();

            UPDATE = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImageResource(),
                            rectSrc, rectDst, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
