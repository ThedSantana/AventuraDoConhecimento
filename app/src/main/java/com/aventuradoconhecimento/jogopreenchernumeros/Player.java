package com.aventuradoconhecimento.jogopreenchernumeros;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Bitmap image;
    private long startTime;

    public Player(Bitmap res, int xAxys, int yAxys) {
        x = xAxys;
        y = yAxys;
        score = 0;
        image = res;

        startTime = System.nanoTime();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,null);
    }

    public int getScore() {
        return score;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetScore() {
        score = 0;
    }
}