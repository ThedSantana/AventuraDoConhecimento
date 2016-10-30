package br.com.lealweb.aventuradoconhecimento.jogomemoria.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryView;
import br.com.lealweb.aventuradoconhecimento.jogomemoria.R;

public class DificultyIcon extends GameObject {

    public DificultyIcon(Context c) {
        super(c);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jm_dificulty);
        width = image.getWidth();
        height = image.getHeight();
    }

    public void update() {
        y = MemoryView.GameMetrics.SCREEN_HEIGHT - height;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
