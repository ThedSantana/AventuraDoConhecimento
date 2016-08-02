package br.com.lealweb.aventuradoconhecimento.jogomemoria;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.HighScoreDatabase.HighScoreEntry;

public class ListHighScoresActivity extends ListActivity {

	public static String JUST_STORED = "br.com.lealweb.aventuradoconhecimento.jogomemoria.just_stored";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		HighScoreDatabase db = HighScoreDatabase.getDatabase(this);
		final List<String> list = new ArrayList<String>();
		for (HighScoreEntry entry : db.getSortedHighScores()) {
			list.add(entry.score + " pontos - " + entry.name);
		}

		setContentView(R.layout.highscorelist);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.highscore_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
