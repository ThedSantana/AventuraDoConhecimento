package br.com.lealweb.aventuradoconhecimento.jogomemoria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.model.DificultyIcon;
import br.com.lealweb.aventuradoconhecimento.jogomemoria.model.ExitIcon;
import br.com.lealweb.aventuradoconhecimento.jogomemoria.repositorie.Dificulty;

public class MemoryView extends View {

    public static class GameMetrics {

        public static final int SCREEN_WIDTH_RATIO = 1000;

        public static final int SCREEN_HEIGHT_RATIO = 600;
        public static int SCREEN_WIDTH;
        public static int SCREEN_HEIGHT;

        int boardHeight;
        int boardWidth;

        int offsetX;

        int offsetY;
        int paddingBetweenTiles;
        int tileSize;

    }

    private Bitmap clearBitmap;
    final MemoryGame game;
    private GameMetrics metrics;
    private Bitmap[] tiles;
    final Timer timer = new Timer(true);
    private Bitmap unknownBitmap;
    private MemoryActivity ma;

    private boolean needShowDialog = true;
    private DificultyIcon dificultyIcon;
    private ExitIcon exitIcon;

    public MemoryView(Context context, AttributeSet attributes) {
        super(context, attributes);

        ma = (MemoryActivity) context;

        this.game = ma.game;
        this.dificultyIcon = new DificultyIcon(context);
        this.exitIcon = new ExitIcon(context);

        SoundManager.initSounds(context);
    }

    private GameMetrics computeMetrics() {
        GameMetrics m = new GameMetrics();

        DisplayMetrics displaymetrics = getContext().getResources().getDisplayMetrics();
        m.SCREEN_WIDTH = displaymetrics.widthPixels;
        m.SCREEN_HEIGHT = displaymetrics.heightPixels;

        int numberOfHorizontalPaddings = game.width + 1;
        int numberOfVerticalPaddings = game.height + 1;
        final int WANTED_PADDING_PX = 2;
        int totalVerticalPadding = numberOfVerticalPaddings * WANTED_PADDING_PX;
        int totalHorizontalPadding = numberOfHorizontalPaddings * WANTED_PADDING_PX;

        m.tileSize = Math.min((getHeight() - totalVerticalPadding) / game.height, (getWidth() - totalHorizontalPadding)
                / game.width);
        int neededWidth = m.tileSize * game.width;
        int neededHeight = m.tileSize * game.height;

        int verticalSpaceOver = getHeight() - neededHeight;
        int horizontalSpaceOver = getWidth() - neededWidth;
        m.paddingBetweenTiles = Math.min(verticalSpaceOver / numberOfVerticalPaddings, horizontalSpaceOver
                / numberOfHorizontalPaddings);

        m.boardWidth = game.width * m.tileSize + (game.width - 1) * m.paddingBetweenTiles;
        m.boardHeight = game.height * m.tileSize + (game.height - 1) * m.paddingBetweenTiles;

        m.offsetX = (getWidth() - m.boardWidth) / 2;
        m.offsetY = (getHeight() - m.boardHeight) / 2;

        return m;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.setDensity(Bitmap.DENSITY_NONE);

        dificultyIcon.update();
        dificultyIcon.draw(canvas);

        exitIcon.update();
        exitIcon.draw(canvas);
        
        drawCards(canvas);
        drawScore(canvas);
    }

    private void drawCards(Canvas canvas) {
        for (int x = 0; x < game.width; x++) {
            for (int y = 0; y < game.height; y++) {
                int left = metrics.offsetX + (metrics.tileSize + metrics.paddingBetweenTiles) * x;
                int top = metrics.offsetY + (metrics.tileSize + metrics.paddingBetweenTiles) * y;
                Integer bitmapIndex = game.displayedBoard[x][y];
                Bitmap bitmap = getBitmapFromIndex(bitmapIndex);
                canvas.drawBitmap(bitmap, left, top, null);
            }
        }
    }

    private void drawScore(Canvas canvas) {
        ma.updateScore(game.getScore());
    }

    private void changeDificulty() {
        game.changeDificulty();
        newGame();
    }

    private void newGame() {
        game.restart();
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        invalidate();
    }

    void gameOver() {
        SoundManager.playGameDone();
        final int usedSeconds = game.getUsedTimeSeg();
        final int finalScore = game.getScore();

        final String LAST_NAME_KEY = "last_name";
        SharedPreferences prefs = ((Activity) getContext()).getPreferences(Context.MODE_PRIVATE);
        String initialName = prefs.getString(LAST_NAME_KEY, "");

        final HighScoreDatabase db = HighScoreDatabase.getDatabase(getContext());
        int position = db.getPositionForScore(finalScore);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false);

        if (position >= HighScoreDatabase.MAX_ENTRIES) {
            alert.setMessage("Pontos: " + finalScore + "\n"
                    + "Tempo: " + usedSeconds + " segundos.");
            alert.setPositiveButton("Ok", null);
        } else {
            alert.setMessage("Parabéns você bateu uma pontuação! \n\n"
                            + "Posição no ranking: " + position + "\n"
                            + "Pontos: " + finalScore + "\n"
                            + "Tempo: " + usedSeconds + " segundos.\n\n"
                            + "Digite seu nome:");
            final EditText textInput = new EditText(getContext());
            textInput.setText(initialName);
            textInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            alert.setView(textInput);
            alert.setPositiveButton("Confirmar", saveOnRanking(
                    finalScore, LAST_NAME_KEY, db, textInput));
        }
        alert.show();

        invalidate();
    }

    @NonNull
    private DialogInterface.OnClickListener saveOnRanking(final int finalScore, final String LAST_NAME_KEY, final HighScoreDatabase db, final EditText textInput) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = textInput.getText().toString();

                Editor editor = ((Activity) getContext()).getPreferences(Context.MODE_PRIVATE).edit();
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
                    ma.finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private Bitmap getBitmapFromIndex(int index) {
        switch (index) {
            case MemoryGame.INT_CLEAR:
                return clearBitmap;
            case MemoryGame.INT_UNKNOWN:
                return unknownBitmap;
            default:
                return tiles[index];
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        metrics = computeMetrics();

        Resources resources = getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final int IMAGE_SIZE = 64;
        float scaleFactor = metrics.tileSize / (float) IMAGE_SIZE;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleFactor, scaleFactor);

        unknownBitmap = BitmapFactory.decodeResource(resources, R.drawable.card, options);
        unknownBitmap = Bitmap.createBitmap(unknownBitmap, 0, 0, IMAGE_SIZE, IMAGE_SIZE, matrix, true);

        clearBitmap = Bitmap.createBitmap(metrics.tileSize, metrics.tileSize, Config.ARGB_8888);
        clearBitmap.eraseColor(Color.TRANSPARENT);

        int[] tilesIndices = new int[]{
                R.drawable.abobora, R.drawable.beholder, R.drawable.bruxa,
                R.drawable.bruxa1, R.drawable.casa_assombrada, R.drawable.ciclope,
                R.drawable.dragao, R.drawable.fantasma, R.drawable.frankestein,
                R.drawable.gato_preto, R.drawable.godzzila, R.drawable.gremlin,
                R.drawable.medusa, R.drawable.mumia, R.drawable.raio,
                R.drawable.robo, R.drawable.vampiro, R.drawable.zoombie
        };

        tiles = new Bitmap[game.getAmountOfCards()];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = BitmapFactory.decodeResource(resources, tilesIndices[i], options);
            tiles[i] = Bitmap.createBitmap(tiles[i], 0, 0, IMAGE_SIZE, IMAGE_SIZE, matrix, true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getAction() & MotionEvent.ACTION_MASK;
        if (actionMasked != MotionEvent.ACTION_DOWN && actionMasked != MotionEvent.ACTION_POINTER_DOWN)
            return false;
        if (game.isWaitingForTimeout())
            return false;

        int x = ((int) event.getX(event.getActionIndex())) - metrics.offsetX + metrics.paddingBetweenTiles;
        int y = ((int) event.getY(event.getActionIndex())) - metrics.offsetY + metrics.paddingBetweenTiles;

        if (exitIcon.isTouched(event)) {
            new ConfirmDialog(getContext(), dialogExitListener)
                    .show(getResources().getString(R.string.exit_dialog));
            return true;
        } else if (dificultyIcon.isTouched(event)) {
            if (needShowDialog) {
                new ConfirmDialog(getContext(), dialogChangeDificultyListener)
                        .show(getResources().getString(R.string.change_dificulty_dialog));
            } else {
                changeDificulty();
            }
            return true;
        } else if (game.isDone()) {
            new ConfirmDialog(getContext(), dialogNewGameListener)
                    .show(getResources().getString(R.string.new_game_dialog));
            return true;
        }
        needShowDialog = true;

        if (!game.isStarted()) {
            game.start();
        }

        // cards click
        int xTile = x / (metrics.tileSize + metrics.paddingBetweenTiles);
        int yTile = y / (metrics.tileSize + metrics.paddingBetweenTiles);
        if (xTile >= game.width || yTile >= game.height)
            return false;

        if (game.clickOnCard(xTile, yTile)) {
            performHapticFeedback(HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING/* =1=VIRTUAL_KEY */);
            if (game.isWaitingForTimeout()) {
                startTimeoutCountdown();
                if (game.wasLastClickIncorrect()) {
                    SoundManager.playIncorrect();
                    game.minusScore();
                } else {
                    SoundManager.playClick();
                    SoundManager.playCorrect();
                    game.plusScore();
                }
            } else {
                SoundManager.playClick();
            }
        }
        invalidate();
        return true;
    }

    public void startTimeoutCountdown() {
        final long delay = 400;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        game.afterTimeout();
                        invalidate();
                        if (game.isDone())
                            gameOver();
                    }
                });
            }
        }, delay);
    }

}
