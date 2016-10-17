package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;

/**
 * Created by leonardoleal on 31/08/16.
 */
public class Player {

    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public Player() {
        this("");
    }

    public void drawScore(Canvas canvas) {
        Paint paintText = new Paint();
        paintText.setTextSize(30 * GameUtil.SCREEN_WIDTH / GameUtil.SCREEN_WIDTH_RATIO);
        paintText.setFakeBoldText(true);
        paintText.setColor(Color.BLACK);

        canvas.drawText(
                "Pontos",
                15 * GameUtil.SCREEN_WIDTH / GameUtil.SCREEN_WIDTH_RATIO,
                35 * GameUtil.SCREEN_HEIGHT / GameUtil.SCREEN_HEIGHT_RATIO,
                paintText
        );

        canvas.drawText(
                String.valueOf(score),
                15 * GameUtil.SCREEN_WIDTH / GameUtil.SCREEN_WIDTH_RATIO,
                70 * GameUtil.SCREEN_HEIGHT / GameUtil.SCREEN_HEIGHT_RATIO,
                paintText
        );
    }

    public void addPoints(int points) { score += points; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void subtractPoints(int points) { score -= points; }
}
