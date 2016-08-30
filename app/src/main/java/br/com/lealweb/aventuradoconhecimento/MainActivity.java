package br.com.lealweb.aventuradoconhecimento;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupParameters();

        setContentView(R.layout.activity_main);
    }

    private void setupParameters() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void loadJogoMemoria(View view) {
        Toast.makeText(this, "Jogo da Memória", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,
                br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryActivity.class);
        startActivity(intent);
    }

    public void loadJogoPreencherNumeros(View view) {
        Toast.makeText(this, "Jogo de Preencher Números", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,
                br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros.GameActivity.class);
        startActivity(intent);
    }

    public void loadJogoMontarPalvras(View view) {
        Toast.makeText(this, "Jogo de Montar Palavras", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,
                br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.WordActivity.class);
        startActivity(intent);
    }

}
