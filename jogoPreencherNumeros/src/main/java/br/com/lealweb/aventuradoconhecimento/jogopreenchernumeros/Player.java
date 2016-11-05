package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Player extends GameObject {

    private Rect rectDst;

    private boolean playing;
    private List<Number> numbers;
    private int color;
    private String name;
    private boolean won;

    public Player(Context context, Bitmap res, int xAxis, int yAxis, int playerColor, String playerName) {
        super(context);
        image = res;
        x = xAxis;
        y = yAxis;

        numbers = new ArrayList<>();
        color = playerColor;
        name = playerName;
        won = false;

        createNumbers();
        updateDistortion();
    }

    public String getName() {
        return name;
    }

    private void createNumbers() {
        Random rand = new Random();
        Point pos;
        List<Point> listPos = new ArrayList<>();
        listPos.add(new Point(GameView.getHeightProportion(x + 30), GameView.getHeightProportion(y + 150)));
        listPos.add(new Point(GameView.getWidthProportion(x + 40), GameView.getHeightProportion(y + 240)));
        listPos.add(new Point(GameView.getWidthProportion(x + 70), GameView.getHeightProportion(y + 85)));
        listPos.add(new Point(GameView.getWidthProportion(x + 115), GameView.getHeightProportion(y + 185)));
        listPos.add(new Point(GameView.getWidthProportion(x + 135), GameView.getHeightProportion(y + 50)));
        listPos.add(new Point(GameView.getWidthProportion(x + 170), GameView.getHeightProportion(y + 120)));
        listPos.add(new Point(GameView.getWidthProportion(x + 190), GameView.getHeightProportion(y + 240)));
        listPos.add(new Point(GameView.getWidthProportion(x + 230), GameView.getHeightProportion(y + 180)));
        listPos.add(new Point(GameView.getWidthProportion(x + 260), GameView.getHeightProportion(y + 85)));
        listPos.add(new Point(GameView.getWidthProportion(x + 280), GameView.getHeightProportion(y + 240)));
        listPos.add(new Point(GameView.getWidthProportion(x + 310), GameView.getHeightProportion(y + 150)));
        int listPosSize = listPos.size();
        int minValue;
        int maxValue;

        minValue = GameView.getDificulty().ordinal() + 1;
        maxValue = minValue * 6 - minValue;

        if (GameView.getDificulty() == GameView.Dificulty.ONE_DICE) {
            listPosSize = 6;
        }

        Map<Integer, Integer> values = new HashMap<>();
        while (values.size() < listPosSize) {
            int value = rand.nextInt(maxValue+1) + minValue;
            values.put(value, value);
        }

        for (Map.Entry<Integer, Integer> value: values.entrySet()) {
            pos = listPos.remove(rand.nextInt(listPos.size()));

            numbers.add(
                    new Number(context, value.getValue(),
                            pos.x, pos.y, color)
            );
        }
    }

    @Override
    public void update() {}

    public void updateDistortion() {
        x = x * GameView.SCREEN_WIDTH / GameView.SCREEN_WIDTH_RATIO;
        y = y * GameView.SCREEN_HEIGHT / GameView.SCREEN_HEIGHT_RATIO;

        width =
                345 * GameView.SCREEN_WIDTH / GameView.SCREEN_WIDTH_RATIO
        ;

        height =
                345 * GameView.SCREEN_HEIGHT / GameView.SCREEN_HEIGHT_RATIO
        ;

        rectDst = new Rect();
        rectDst.left = getX();
        rectDst.right = getX() + getWidth();
        rectDst.top = getY();
        rectDst.bottom = getY() + getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null, rectDst, null);

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
            if (number.isCorrect(numberToCheck)) {
                if (number.isFilled()) return true;
                if (number.isTouched(event)) {
                    SoundManager.playCorrect();
                    number.fill();
                    checkWon();
                    return true;
                } else {
                    SoundManager.playIncorrect();
                }
            }
        }

        return false;
    }

    public int getColor() {
        return color;
    }

    public boolean isWinner() {
        return won;
    }

    private void checkWon() {
        for (Number number: numbers) {
            if (!number.isFilled()) {
                won = false;
                return;
            }
        }

        SoundManager.playGameDone();
        won = true;
    }

    public boolean existNumber(int result) {
        return numbers.contains(new Number(result));
    }
}