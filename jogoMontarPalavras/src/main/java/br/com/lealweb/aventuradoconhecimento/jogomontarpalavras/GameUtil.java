package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by leonardoleal on 28/08/16.
 */
public class GameUtil {

    public static final int LANDSCAPE = 0;
    public static final int PORTRAIT = 1;

    public static final int FPS = 30;

    public static int ORIENTATION;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH_RATIO;
    public static int SCREEN_HEIGHT_RATIO;

    public static float DISTORTION = 1.0f;
    public static AssetManager assetManager;

    private static final String TAG = "GameUtil";

    public static Bitmap decodeImage(String assetName) {
        try {
            InputStream is = GameUtil.assetManager.open(assetName);

            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.d(TAG, "Erro ao decodificar imagem");
        }

        return null;
    }
}

