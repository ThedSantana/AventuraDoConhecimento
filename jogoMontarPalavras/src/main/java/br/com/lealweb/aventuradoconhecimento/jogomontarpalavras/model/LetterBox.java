package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

public class LetterBox extends GameObject {

    private static final String TAG = "LetterBox";

    private Character value;
    private final int letterPosition;
    private final int wordLength;
    private int letterPadding = 10;

    private boolean empty = true;

    public LetterBox(char character, int pos, int wordLength) {
        value = character;
        letterPosition = pos;
        this.wordLength = wordLength;
        setImageResource(GameUtil.decodeImage("letter/box.png"));

        setWidth(imageResource.getWidth());
        setHeight(imageResource.getHeight());

        rectSrc = new Rect(0, 0, getWidth(), getHeight());
        rectDst = new Rect();

        updateDistortion();
    }

    @Override
    public void update() {
        if (UPDATE) {
            int halfWidth = GameUtil.SCREEN_WIDTH / 2;
            float halfWordLenght = wordLength / 2;
            int posX;

            if (letterPosition < halfWordLenght) {
                posX = (int) (halfWidth - (getWidth()/2)
                                - getWidth() *(halfWordLenght-letterPosition));
            } else if(letterPosition > halfWordLenght) {
                posX = (int) (halfWidth + (getWidth()/2)
                                + getWidth() * -(halfWordLenght-letterPosition+1));
            } else {
                posX = halfWidth - getWidth()/2;
            }
            setX(posX);
            setY((int) ((GameUtil.SCREEN_HEIGHT - getHeight()) * 0.4));

            rectDst.left = getX() + letterPadding;
            rectDst.right = getX() + getWidth();
            rectDst.top = getY();
            rectDst.bottom = getY() + getHeight();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImageResource(),
                rectSrc, rectDst, null);
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Bitmap getImageResource() {
        return imageResource;
    }

    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }

    public Character getValue() {
        return value;
    }
}
