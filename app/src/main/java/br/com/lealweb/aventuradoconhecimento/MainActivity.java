package br.com.lealweb.aventuradoconhecimento;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryActivity;
import br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void loadJogoMemoria(View view) {
        Toast.makeText(this, "Jogo da Memória", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MemoryActivity.class);
        startActivity(intent);
    }

    public void loadJogoPreencherNumeros(View view) {
        Toast.makeText(this, "Jogo de Preencher Números", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
