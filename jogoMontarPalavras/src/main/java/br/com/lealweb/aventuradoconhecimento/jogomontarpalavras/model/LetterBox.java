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
        setImageResource(GameUtil.decodeImage("letter/box.png"));
        setValue(character);
        setWidth(imageResource.getWidth());
        setHeight(imageResource.getHeight());
        this.letterPosition = pos;
        this.wordLength = wordLength;

        rectSrc = new Rect(0, 0, getWidth(), getHeight());
        rectDst = new Rect();

        updateDistortion();
    }

    @Override
    public void update() {
        if (UPDATE) {
            int portion = (GameUtil.SCREEN_WIDTH - letterPadding * wordLength) / wordLength;
            setX(portion + (getWidth() * letterPosition));
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

    public void setValue(Character name) {
        this.value = name;
    }
}
