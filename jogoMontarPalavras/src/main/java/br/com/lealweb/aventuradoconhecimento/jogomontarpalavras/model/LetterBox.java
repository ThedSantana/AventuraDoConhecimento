package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

public class LetterBox extends GameObject {

    private static final String TAG = "LetterBox";

    private Character letter;
    private final int letterPosition;
    private final int wordLength;
    private int letterPadding = 10;

    public LetterBox(char letter, int pos, int wordLength) {
        setImageResource(GameUtil.decodeImage("letter/box.png"));
        setLetter(letter);
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

            UPDATE = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImageResource(),
                rectSrc, rectDst, null);
    }

    public Bitmap getImageResource() {
        return imageResource;
    }

    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }

    public Character getName() {
        return letter;
    }

    public void setLetter(Character name) {
        this.letter = name;
    }
}
