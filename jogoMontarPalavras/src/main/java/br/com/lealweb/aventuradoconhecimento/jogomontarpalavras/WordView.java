package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Background;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.DificultyIcon;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.ExitIcon;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Figure;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Letter;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.LetterBox;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Player;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Dificulty;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Figuries;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.HighScoreDatabase;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Letters;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.SoundManager;

import static br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Dificulty.DOUBLE_LETTERS;
import static br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.Dificulty.ONLY_WORD;

public class WordView extends View implements Runnable {

    private static final String TAG = "WordView";

    private boolean running;
    private boolean update;
    private boolean needShowDialog = true;
    private boolean canStartNewGame;
    private WordActivity wordActivity;

    private Background bg;
    private Player player;

    private Figure actualFigure;
    private List<LetterBox> emptyBoxes;

    private List<Letter> letterBoxes;
    private Letter letterToMove;

    private ExitIcon exitIcon;
    private DificultyIcon dificultyIcon;

    // repositories
    private Letters letters;
    private Figuries figuries;
    private Dificulty dificulty;

    public WordView(Context context) {
        super(context);

        wordActivity = (WordActivity) context;

        init();
    }

    public void init() {
        setRunning(true);
        update = true;

        // configuração do background
        bg = new Background();
        GameUtil.DISTORTION =
                (float) GameUtil.SCREEN_WIDTH / bg.getWidth();
        bg.updateDistortion(GameUtil.DISTORTION);

        SoundManager.initSounds(getContext());

        exitIcon = new ExitIcon();
        dificultyIcon = new DificultyIcon();

        dificulty = ONLY_WORD;

        newGame();
    }

    private void newGame() {
        figuries = new Figuries();
        letters = new Letters();
        player = new Player();

        newTurn();
    }

    private void newTurn() {
        try {
            actualFigure = figuries.getFigureAleatorie();

            emptyBoxes = new ArrayList<LetterBox>();
            letterBoxes = new ArrayList<Letter>();

            char[] wordLetters = actualFigure.getName().toCharArray();
            for (int pos = 0; pos < wordLetters.length; pos++) {
                generateEmptyBoxes(wordLetters, pos);
                generateLetterBoxes(wordLetters, pos);

                if (dificulty.equals(DOUBLE_LETTERS)) {
                    generateRandomLetterBoxes();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao iniciar novo turno");
            e.printStackTrace();
        }
    }

    private void changeDificulty() {
        switch (dificulty) {
            case ONLY_WORD:
                dificulty = DOUBLE_LETTERS;
                break;

            case DOUBLE_LETTERS:
                dificulty = ONLY_WORD;
                break;
        }
        newGame();
    }

    private void generateEmptyBoxes(char[] wordLetters, int pos) {
        emptyBoxes.add(new LetterBox(wordLetters[pos], pos, wordLetters.length));
    }

    private void generateLetterBoxes(char[] wordLetters, int pos) {
        boolean isOnEmptySpace;
        Letter letter;
        do {
            isOnEmptySpace = true;

            letter = letters.getLetterByValue(wordLetters[pos]);

            for (Letter l: letterBoxes) {
                if (l.isTouched(letter.getX(), letter.getY())) {
                    isOnEmptySpace = false;
                }
            }

        } while (! isOnEmptySpace);

        letterBoxes.add(letter);
    }

    private void generateRandomLetterBoxes() {
        boolean isOnEmptySpace;
        Letter letter;
        do {
            isOnEmptySpace = true;

            letter = letters.getRandomLetter();

            for (Letter l: letterBoxes) {
                if (l.isTouched(letter.getX(), letter.getY())) {
                    isOnEmptySpace = false;
                }
            }

        } while (! isOnEmptySpace);

        letterBoxes.add(letter);
    }


    public void update() {
        if (update) {
            bg.update();
            dificultyIcon.update();
            exitIcon.update();
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
        dificultyIcon.draw(canvas);
        exitIcon.draw(canvas);
        actualFigure.draw(canvas);

        for (LetterBox lb : emptyBoxes) {
            lb.draw(canvas);
        }

        for (Letter l : letterBoxes) {
            l.draw(canvas);
        }

        player.drawScore(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                for (Letter l : letterBoxes) {
                    if (l.isTouched(event.getX(), event.getY())) {
                        letterToMove = l;
                        SoundManager.playClick();
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

            case MotionEvent.ACTION_UP: {

                if (exitIcon.isTouched(event.getX(), event.getY())) {
                    new ConfirmDialog(getContext(), dialogExitListener)
                            .show(getResources().getString(R.string.exit_dialog));
                    return true;
                }

                if (dificultyIcon.isTouched(event.getX(), event.getY())) {
                    if (needShowDialog) {
                        new ConfirmDialog(getContext(), dialogChangeDificultyListener)
                                .show(getResources().getString(R.string.change_dificulty_dialog));
                    } else {
                        changeDificulty();
                    }
                    return true;
                }
                needShowDialog = true;

                if (letterToMove != null) {
                    for (LetterBox lB : emptyBoxes) {
                        if (lB.isTouched(event.getX(), event.getY())
                                && lB.isEmpty()
                                ) {
                            if (letterToMove.getValue() == lB.getValue()) {
                                SoundManager.playCorrect();
                                player.addPoints(1);

                                lB.setEmpty(false);
                                lB.setImageResource(letterToMove.getImageResource());

                                letterBoxes.remove(letterToMove);

                                if (wordCompleted()) {
                                    SoundManager.playGameDone();

                                    if (gameOver()) {
                                        finishGame();
                                    } else {
                                        newTurn();
                                    }
                                }

                            } else {
                                SoundManager.playIncorrect();
                                player.subtractPoints(3);
                            }
                        }
                    }

                    if (letterToMove.getY() > GameUtil.SCREEN_HEIGHT / 2) {
                        letterToMove.drop(false);
                    } else {
                        letterToMove.drop(true);
                    }
                    letterToMove = null;
                } else if (canStartNewGame) {
                    new ConfirmDialog(getContext(), dialogNewGameListener)
                            .show(getResources().getString(R.string.new_game_dialog));
                }
            }
            break;
        }
        return true;
    }

    private boolean gameOver() { return figuries.isEmpty(); }

    private boolean wordCompleted() {
        for (LetterBox box: emptyBoxes) {
            if (box.isEmpty())
                return false;
        }
        return true;
    }

    private void finishGame() {
        canStartNewGame = true;

        SharedPreferences prefs = ((Activity) getContext()).getPreferences(Context.MODE_PRIVATE);
        player.setName(prefs.getString("last_name", ""));

        final HighScoreDatabase db = HighScoreDatabase.getDatabase(getContext());
        int position = db.getPositionForScore(player.getScore());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false);

        if (position >= HighScoreDatabase.MAX_ENTRIES) {
            alert.setMessage("Pontos: " + player.getScore());
            alert.setPositiveButton("Ok", null);
        } else {
            alert.setMessage("Parabéns você bateu uma pontuação! \n\n"
                    + "Posição no ranking: " + position + "\n"
                    + "Pontos: " + player.getScore() + "\n"
                    + "Digite seu nome:");
            final EditText textInput = new EditText(getContext());
            textInput.setText(player.getName());
            textInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            alert.setView(textInput);
            alert.setPositiveButton("Confirmar", saveOnRanking(
                    player.getScore(), player.getName(), db, textInput));
        }
        alert.show();

        invalidate();
    }

    @NonNull
    private DialogInterface.OnClickListener saveOnRanking(final int finalScore, final String LAST_NAME_KEY, final HighScoreDatabase db, final EditText textInput) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = textInput.getText().toString();

                SharedPreferences.Editor editor = ((Activity) getContext()).getPreferences(Context.MODE_PRIVATE).edit();
                editor.putString(LAST_NAME_KEY, value);
                editor.commit();

                db.addEntry(value, finalScore);

                showHighScoreDialog();
            }
        };
    }

    private void showHighScoreDialog() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ListHighScoresActivity.class);
        getContext().startActivity(intent);
    }

    DialogInterface.OnClickListener dialogChangeDificultyListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    changeDificulty();
                    needShowDialog = false;
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogNewGameListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    newGame();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogExitListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    wordActivity.finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

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
            e.printStackTrace();
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}