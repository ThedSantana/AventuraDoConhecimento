package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.Serializable;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

/**
 * Created by leonardoleal on 28/08/16.
 */
public abstract class GameObject implements Serializable {

    private static final String TAG = "GameObject";

    private int x;
    private int y;
    private int width;
    private int height;
    protected Bitmap imageResource;
    protected Rect rectSrc;
    protected Rect rectDst;

    protected boolean UPDATE = true;

    public abstract void update();
    public abstract void draw(Canvas canvas);

    public void updateDistortion() {
        width = (int) (width * GameUtil.DISTORTION);
        height = (int) (height * GameUtil.DISTORTION);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public Bitmap getImageResource() {
        return imageResource;
    }

    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }
}
