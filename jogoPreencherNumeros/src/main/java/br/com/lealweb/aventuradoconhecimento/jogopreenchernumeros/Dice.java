package br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Dice extends GameObject {

    private int result, frames;
    private Animation animation = new Animation();
    private boolean rolling;

    public Dice(Context context, int xAxys, int yAxis) {
        super(context);
        x = xAxys;
        y = yAxis;

        setImage();
    }

    public void startRolling() {
        rolling = true;
        resetDice();
        SoundManager.playDiceShake();
    }

    public void stopRolling() {
        rolling = false;
        raffleResult();
        SoundManager.playDiceThrow();
    }

    public boolean isRolling() {
        return rolling;
    }

    private void raffleResult() {
        Random rand = new Random();
        result = rand.nextInt(6) + 1;
        setImage();
    }

    @Override
    public void update() {
        animation.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getResult() {
        return result;
    }

    public void resetDice() {
        result = 0;
        setImage();
    }

    public void config() {
        height = image.getHeight();
        width = image.getWidth()/frames;

        Bitmap[] image = new Bitmap[frames];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(this.image, i * width, 0, width, height);
        }

        animation.setFrames(image);
    }

    private void setImage() {
        frames = 1;

        switch (result) {
            case 1:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_1);
                break;

            case 2:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_2);
                break;

            case 3:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_3);
                break;

            case 4:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_4);
                break;

            case 5:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_5);
                break;

            case 6:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice_6);
                break;

            default:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.jpn_dice);
                frames = 16;
        }

        config();
    }
}