package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class DificultyIcon extends GameObject {

    public DificultyIcon(Context c) {
        super(c);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dificulty);
        width = image.getWidth();
        height = image.getHeight();
        y = GameView.SCREEN_HEIGHT - height;
        x = 10;
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
