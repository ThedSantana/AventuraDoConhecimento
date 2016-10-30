package br.com.lealweb.aventuradoconhecimento.jogomemoria.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryView;
import br.com.lealweb.aventuradoconhecimento.jogomemoria.R;

public class ExitIcon extends GameObject {

    public ExitIcon(Context c) {
        super(c);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jm_exit);
        width = image.getWidth();
        height = image.getHeight();
    }

    public void update() {
        x = MemoryView.GameMetrics.SCREEN_WIDTH - width;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
