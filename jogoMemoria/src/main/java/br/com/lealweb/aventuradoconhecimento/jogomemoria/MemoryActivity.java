package br.com.lealweb.aventuradoconhecimento.jogomemoria;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryGame.GameListener;

public class MemoryActivity extends Activity {

	private static final String GAME_KEY = "gameMemory";

	MemoryGame game;
	private MemoryView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupParameters();

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		if (savedInstanceState != null) {
			game = (MemoryGame) savedInstanceState.getSerializable(GAME_KEY);
		}
		if (game == null) {
			game = new MemoryGame(6, 6);
		}
		setContentView(R.layout.memory_activity);

		GameListener listener = new GameListener() {
			@Override
			public void gameOver(MemoryGame game) {}

			public void gamePaused(MemoryGame game) {}

			public void gameResumed(MemoryGame game) {}

			@Override
			public void gameStarted(MemoryGame game) {}
		};
		game.setListener(listener);

		view = (MemoryView) findViewById(R.id.memoryview);

		if (game.isWaitingForTimeout()) {
			// retorna contgem tempo caso pausado
			view.startTimeoutCountdown();
		}
	}

	private void setupParameters() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.restart_menuitem) {
			game.restart();
			view.invalidate();
			return true;
		}

		if (item.getItemId() == R.id.highscore_menuitem) {
			startActivity(new Intent(this, ListHighScoresActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		game.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (game.isStarted()) {
			game.resume();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(GAME_KEY, game);
	}

	public void updateScore(int score) {
		TextView TVScore = (TextView) findViewById(R.id.score);
		TVScore.setText("Pontos \n" + String.valueOf(score));
	}
}
