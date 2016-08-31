package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
        paintText.setTextSize(18);
        paintText.setFakeBoldText(true);
        paintText.setColor(Color.BLACK);
        canvas.drawText(
                "Pontos"
                , 10, 20
                , paintText
        );

        canvas.drawText(
                String.valueOf(score)
                , 10, 35
                , paintText
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
