package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class WordActivity extends Activity {

    WordView wordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setupParameters();

        wordView = new WordView(this);
        setContentView(wordView);

        Thread thread = new Thread(wordView);
        thread.start();
    }

    public void setupParameters() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        GameUtil.ORIENTATION = getRequestedOrientation();
        GameUtil.SCREEN_WIDTH = displaymetrics.widthPixels;
        GameUtil.SCREEN_HEIGHT = displaymetrics.heightPixels;
        GameUtil.assetManager = super.getAssets();
        GameUtil.SCREEN_WIDTH_RATIO = 1000;
        GameUtil.SCREEN_HEIGHT_RATIO = 600;
    }
}
