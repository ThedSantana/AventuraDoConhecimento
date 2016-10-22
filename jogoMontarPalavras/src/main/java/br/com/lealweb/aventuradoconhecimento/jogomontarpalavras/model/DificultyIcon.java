package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.InputStream;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

public class DificultyIcon extends GameObject {

    private static final String TAG = "DificultyIcon";

    public DificultyIcon() {
        try {
            InputStream is = GameUtil.assetManager.open("dificulty_icon.png");
            imageResource = BitmapFactory.decodeStream(is);
            setHeight(imageResource.getHeight());
            setWidth(imageResource.getWidth());

            rectSrc = new Rect(0, 0, getWidth(), getHeight());
            rectDst = new Rect();

        } catch (Exception e) {
            Log.d(TAG, "Erro ao decodificar a imagem do fundo.");
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (UPDATE) {
            setY((GameUtil.SCREEN_HEIGHT - getHeight()));

            rectDst.left = getX();
            rectDst.right = getX() + getWidth();
            rectDst.top = getY();
            rectDst.bottom = getY() + getHeight();

            UPDATE = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(imageResource, rectSrc, rectDst, null);
    }
}
