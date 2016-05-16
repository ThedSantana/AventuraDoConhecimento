package com.aventuradoconhecimento.jogopreenchernumeros;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


    private enum Actions {
        ROLL_DICE, CHOSSE_NUMBER
    }

    public static int WIDTH = 800;
    public static int HEIGHT = 480;
    public static final int MOVESPEED = 0;

    private MainThread thread;
    private Background bg;
    private Paint paintText;
    private Player player1, player2;
    private Dice dice, dice2;
    private Actions action;
    private Player playerTurn;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);

        thread = new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Log.v("EVENT", "SizeChanged! " + w + " " + h);
        //Log.v("EVENT", "OldValues! " + oldw + " " + oldh);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch(InterruptedException e) { e.printStackTrace(); }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintText.setTextSize(18);
        paintText.setFakeBoldText(true);

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.jpn_background));

        player1 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.jpn_player1_elephant)
                , 30, 110, Color.YELLOW, "Jogador 1"
        );
        player2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.jpn_player2_elephant)
                , 470, 110, Color.GREEN, "Jogador 2"
        );

        // passa o sprite e não a imagem da frente do dado
        dice = new Dice(getContext(), 250, 5);
        dice2 = new Dice(getContext(), 450, 5);

        playerTurn = player1;
        playerTurn.setPlaying(true);
        action = Actions.ROLL_DICE;

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (action.equals(Actions.ROLL_DICE)) {
                    if (!dice.isRolling()) {
                        dice.startRolling();
                        dice2.startRolling();
                    } else {
                        dice.stopRolling();
                        dice2.stopRolling();
                        action = Actions.CHOSSE_NUMBER;
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (action.equals(Actions.CHOSSE_NUMBER)) {
                    int result = dice.getResult() + dice2.getResult();

                    if (playerTurn.verifyTouchedNumber(result, event)) {
                        if (player1.isPlaying()) {
                            player1.setPlaying(false);
                            playerTurn = player2;
                        } else {
                            player2.setPlaying(false);
                            playerTurn = player1;
                        }

                        playerTurn.setPlaying(true);
                        action = Actions.ROLL_DICE;
                    }
                }
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }

    public void update() {
        if (dice.isRolling()) {
            dice.update();
            dice2.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player1.draw(canvas);
            player2.draw(canvas);
            dice.draw(canvas);
            dice2.draw(canvas);
            canvas.restoreToCount(savedState);
        }

        canvas.drawText("É a vez do "+ playerTurn.getName(), 20, 20, paintText);
    }


}