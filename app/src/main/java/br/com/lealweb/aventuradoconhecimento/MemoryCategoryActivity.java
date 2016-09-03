package br.com.lealweb.aventuradoconhecimento;

import android.content.Intent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.List;


public class MemoryCategoryActivity extends DefaultActivity {

    @Override
    protected List<String> getTitles(){
        return Lists.newArrayList(
                "Jogo da\nMemória");
    }

    @Override
    protected List<Integer> getBgRes(){
        return Lists.newArrayList(
                R.drawable.memory_game_icon);
    }

    @Override
    public void onItemClick(int position, MotionEvent e) {
        if (position ==  0) {
            Toast.makeText(getBaseContext(), "Jogo da Memória", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(),
                    br.com.lealweb.aventuradoconhecimento.jogomemoria.MemoryActivity.class);
           startActivity(intent);
        }
    }
}
