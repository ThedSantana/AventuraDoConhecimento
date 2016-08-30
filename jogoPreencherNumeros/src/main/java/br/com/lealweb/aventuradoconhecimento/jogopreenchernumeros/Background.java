package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background extends GameObject{

    public Background(Context context) {
        super(context);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_background);
        width = image.getWidth();
        height = image.getHeight();
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GameView.WIDTH, y, null);
        }
    }
}