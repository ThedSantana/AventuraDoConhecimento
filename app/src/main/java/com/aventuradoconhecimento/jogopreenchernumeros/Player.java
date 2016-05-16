package com.aventuradoconhecimento.jogopreenchernumeros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends GameObject {

    private boolean playing;
    private Bitmap image;
    private List<Number> numbers;
    private int color;
    private String name;

    public Player(Bitmap res, int xAxis, int yAxis, int playerColor, String playerName) {
        image = res;
        x = xAxis;
        y = yAxis;
        width = image.getWidth();
        height = image.getHeight();
        numbers = new ArrayList<Number>();
        color = playerColor;
        name = playerName;

        createNumbers();
    }


    public String getName() {
        return name;
    }

    private void createNumbers() {
        Random rand = new Random();
        NumberPosition pos;
        List<NumberPosition> listPos = new ArrayList();
        listPos.add(new NumberPosition(x+30, y+150));
        listPos.add(new NumberPosition(x+40, y+240));
        listPos.add(new NumberPosition(x+70, y+85));
        listPos.add(new NumberPosition(x+115, y+185));
        listPos.add(new NumberPosition(x+135, y+50));
        listPos.add(new NumberPosition(x+170, y+120));
        listPos.add(new NumberPosition(x+190, y+250));
        listPos.add(new NumberPosition(x+220, y+190));
        listPos.add(new NumberPosition(x+260, y+85));
        listPos.add(new NumberPosition(x+310, y+150));
        listPos.add(new NumberPosition(x+300, y+230));

        int listPosSize = listPos.size();
        for (int i = 0; i < listPosSize; i++) {
            pos = listPos.remove(rand.nextInt(listPos.size()));

            numbers.add(
                    new Number(i+2, pos.getX(), pos.getY(), color)
            );
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,null);

        for (Number number: numbers) {
            number.draw(canvas);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public boolean verifyTouchedNumber(int chosen, MotionEvent event) {
        for (Number number: numbers) {
            if (number.isTouched(event)) {
                if (number.isCorrect(chosen)) {
                    number.fill();
                    return true;
                }
            }
        }

        return false;
    }
}