package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.HighScoreDatabase.HighScoreEntry;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.HighScoreDatabase;

public class ListHighScoresActivity extends ListActivity {

	public static String JUST_STORED = "br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.just_stored";

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
}
