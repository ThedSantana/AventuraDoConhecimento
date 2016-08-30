package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Background;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Figure;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Letter;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.LetterBox;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Figuries;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Letters;

/**
 * Created by leonardoleal on 28/08/16.
 */
public class WordView extends View implements Runnable {

    private static final String TAG = "WordView";

    private boolean running;
    private boolean update;
    private Background bg;

    // repositories
    private Figuries figuries = new Figuries();
    private Letters letters = new Letters();

    private Figure actualFigure;
    private List<LetterBox> emptyBoxes;
    private ArrayList<Letter> letterBoxes;

    private Letter letterToMove;

    public WordView(Context context) {
        super(context);

        init();
    }

    public void init() {
        setRunning(true);
        update = true;

        // configuração do background
        bg = new Background();
        GameUtil.DISTORTION =
                (float) GameUtil.SCREEN_HEIGHT / bg.getHeight();
        bg.updateDistortion();

        // adiciona figura
        figuries = new Figuries();
        actualFigure = figuries.getFigure();

        emptyBoxes = new ArrayList<LetterBox>();
        letterBoxes = new ArrayList<Letter>();

        char[] wordLetters = actualFigure.getName().toCharArray();
        int wordLength = wordLetters.length;
        for (int pos = 0; pos < wordLength; pos++) {
            // adiciona caixas vazias relativas as letras da palavra
            emptyBoxes.add(new LetterBox(wordLetters[pos], pos, wordLength));

            // adiciona letras relativas a palavra
            letterBoxes.add(letters.getLetterByValue(wordLetters[pos]));

            Log.d(TAG, letters.getLetterByValue(wordLetters[pos]).getValue().toString());
            // adiciona letras aleatórias --QUANDO TIVER DIFICULDADE
        }

    }

    public void update() {
        if (update) {
            bg.update();
            actualFigure.update();

            for (LetterBox lb : emptyBoxes) {
                lb.update();
            }

            for (Letter l : letterBoxes) {
                l.update();
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        bg.draw(canvas);
        actualFigure.draw(canvas);

        for (LetterBox lb : emptyBoxes) {
            lb.draw(canvas);
        }

        for (Letter l : letterBoxes) {
            l.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int positionX = (int) event.getRawX();
        int positionY = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                for (Letter l : letterBoxes) {
                    if (l.isTouched(event)) {
                        letterToMove = l;
                        break;
                    }
                }
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                if (letterToMove != null) {
                    letterToMove.startDrag(event);
//                    Log.d("MOVENDO", "Letra"+letterToMove.getValue());
                }
            }
            break;
            case MotionEvent.ACTION_UP:
                if (letterToMove != null) {
                    for (LetterBox lB: emptyBoxes) {
                        if (lB.isTouched(event)
                            && lB.isEmpty()
                            && letterToMove.getValue() == lB.getValue()
                        ) {
                            lB.setEmpty(false);
                            lB.setImageResource(letterToMove.getImageResource());
                            letterBoxes.remove(letterToMove);
                        } else {
                            letterToMove.drop(true);
                        }
                    }

                    letterToMove = null;
                }
                break;
        }
        return true;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / GameUtil.FPS;
        double averageFPS;

        try {
            while (running) {
                startTime = System.nanoTime();

                synchronized (this) {
                    update();
                    postInvalidate();
                }

                timeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - timeMillis;
                Thread.sleep(waitTime);

                totalTime += System.nanoTime() - startTime;
                frameCount++;
                if (frameCount == GameUtil.FPS) {
                    averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                    frameCount = 0;
                    totalTime = 0;
//                    Log.i("AVG FPS", ""+averageFPS);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Erro no loop principal do jogo.");
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}