package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background extends GameObject {

    private Rect rectDst;

    public Background(Context context) {
        super(context);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_background);
        width = image.getWidth();
        height = image.getHeight();

        rectDst = new Rect(0, 0, GameView.SCREEN_WIDTH, GameView.SCREEN_HEIGHT);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null, rectDst, null);
    }
}