package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ExitIcon extends GameObject {

    public ExitIcon(Context c) {
        super(c);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_exit);
        width = image.getWidth();
        height = image.getHeight();
        x = GameView.SCREEN_WIDTH - width;
        y = 10;
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
