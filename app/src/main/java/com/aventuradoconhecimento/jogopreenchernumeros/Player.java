package com.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
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
    private SoundList sound;

    public Player(Context c, Bitmap res, int xAxis, int yAxis, int playerColor, String playerName) {
        sound = new SoundList(c);
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
        Point pos;
        List<Point> listPos = new ArrayList();
        listPos.add(new Point(x+30, y+150));
        listPos.add(new Point(x+40, y+240));
        listPos.add(new Point(x+70, y+85));
        listPos.add(new Point(x+115, y+185));
        listPos.add(new Point(x+135, y+50));
        listPos.add(new Point(x+170, y+120));
        listPos.add(new Point(x+190, y+240));
        listPos.add(new Point(x+230, y+180));
        listPos.add(new Point(x+260, y+85));
        listPos.add(new Point(x+280, y+240));
        listPos.add(new Point(x+310, y+150));

        int listPosSize = listPos.size();
        for (int i = 0; i < listPosSize; i++) {
            pos = listPos.remove(rand.nextInt(listPos.size()));

            numbers.add(
                    new Number(i+2, pos.x, pos.y, color)
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

    public boolean isTouchedNumber(int numberToCheck, MotionEvent event) {
        for (Number number: numbers) {
            if (number.isTouched(event) || number.isFilled()) {
                if (number.isCorrect(numberToCheck)) {
                    if (!number.isFilled()) {
                        sound.play(R.raw.elephant);
                        number.fill();
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public int getColor() {
        return color;
    }

    public boolean isWinner() {
        for (Number number: numbers) {
            if (!number.isFilled()) {
                return false;
            }
        }

        return true;
    }
}