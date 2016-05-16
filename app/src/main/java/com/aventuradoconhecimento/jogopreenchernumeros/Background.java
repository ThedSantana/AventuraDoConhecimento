package com.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y;

    public Background(Context context) {
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_background);
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }
}