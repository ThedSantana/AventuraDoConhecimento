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
        ROLL_DICE, NEW_GAME, CHOSSE_NUMBER
    }

    public static int WIDTH = 800;
    public static int HEIGHT = 480;
    public static final int MOVESPEED = 0;
    private static final long BLINK_DURATION = 350;

    private MainThread thread;
    private Background bg;
    private Paint paintText;
    private Player player1, player2;
    private Dice dice, dice2;
    private Actions action;
    private Player playerTurn;

    private int blinkColor;
    private long blinkStart;
    private long lastUpdateTime;
    private boolean blink;

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
        paintText.setTextSize(18);
        paintText.setFakeBoldText(true);

        blinkColor = Color.BLUE;

        bg = new Background(getContext());

        dice = new Dice(getContext(), 250, 5);
        dice2 = new Dice(getContext(), 450, 5);

        setNewGame();

        thread.setRunning(true);
        thread.start();
    }

    private void setNewGame() {
        player1 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.jpn_player1_elephant)
                , 30, 110, Color.YELLOW, "Jogador 1"
        );
        player2 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.jpn_player2_elephant)
                , 470, 110, Color.GREEN, "Jogador 2"
        );

        playerTurn = player1;
        playerTurn.setPlaying(true);
        action = Actions.ROLL_DICE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (action.equals(Actions.NEW_GAME)) setNewGame();

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

                    if (playerTurn.isTouchedNumber(result, event)) {
                        if (playerTurn.isWinner()) {
                            action = Actions.NEW_GAME;
                            return true;
                        }

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

        if (System.currentTimeMillis() - lastUpdateTime >= BLINK_DURATION && !blink) {
            blink = true;
            blinkStart = System.currentTimeMillis();
        }
        
        if (System.currentTimeMillis() - blinkStart >= 800 && blink) {
            blink = false;
            lastUpdateTime = System.currentTimeMillis();
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

        paintText.setColor(Color.RED);
        canvas.drawText("É a vez do ", 20, 20, paintText);
        paintText.setColor(playerTurn.getColor());
        canvas.drawText(playerTurn.getName(), 110, 20, paintText);

        if (blink) {
            blinkColor = (blinkColor == Color.BLUE) ? Color.RED :
                    ((blinkColor == Color.YELLOW) ? Color.BLUE : Color.YELLOW);
            paintText.setColor(blinkColor);
        }
        if (playerTurn.isWinner()) {
            canvas.drawText(playerTurn.getName() +" GANHOU!!!", WIDTH / 2 - 100, HEIGHT / 2, paintText);
        }
    }


}