package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.InputStream;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

/**
 * Created by leonardoleal on 28/08/16.
 */
public class Background extends GameObject {

    private static final String TAG = "Background";

    public Background() {
        try {
            InputStream is = GameUtil.assetManager.open("background.png");
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
    public void updateDistortion() {
        super.updateDistortion();

        rectDst.left = 0;
        rectDst.right = getWidth();
        rectDst.top = 0;
        rectDst.bottom = getHeight();
    }

    @Override
    public void update() {
        if (UPDATE) {
            int distortion = (int)(GameUtil.FPS * GameUtil.DISTORTION);

            rectDst.left -= distortion;
            rectDst.right -= distortion;

            UPDATE = false;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(imageResource, rectSrc, rectDst, null);
    }
}
