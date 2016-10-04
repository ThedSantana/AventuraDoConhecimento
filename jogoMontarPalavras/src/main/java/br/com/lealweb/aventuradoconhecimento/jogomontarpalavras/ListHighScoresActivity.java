package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.HighScoreDatabase;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie.HighScoreDatabase.HighScoreEntry;

public class ListHighScoresActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final List<String> list = new ArrayList<String>();

		HighScoreDatabase db = HighScoreDatabase.getDatabase(this);
		List<HighScoreEntry> sortedHighScores = db.getSortedHighScores();

		for (HighScoreEntry entry : sortedHighScores) {
			list.add((sortedHighScores.indexOf(entry) + 1) + " - " + entry.score + " pontos - " + entry.name);
		}

		setContentView(R.layout.highscorelist);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
	}
}
