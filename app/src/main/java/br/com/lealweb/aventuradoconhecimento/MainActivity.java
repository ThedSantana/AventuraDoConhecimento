package br.com.lealweb.aventuradoconhecimento;

import android.content.Intent;
import android.view.MotionEvent;

import com.google.common.collect.Lists;

import java.util.List;


public class MainActivity extends DefaultActivity {

    protected List<String> getTitles(){
        return Lists.newArrayList(
                "123",
                "ABC",
                "☆★☆");
    }

    protected List<Integer> getBgRes(){
        return Lists.newArrayList(
                R.drawable.math_category,
                R.drawable.portuguese_category,
                R.drawable.memory_category);
    }

    @Override
    public void onItemClick(int position, MotionEvent e) {
        if (position ==  0) {
            Intent intent = new Intent(getBaseContext(), MathCategoryActivity.class);
            startActivity(intent);
        } else if (position ==  1) {
            Intent intent = new Intent(getBaseContext(), PortugueseCategoryActivity.class);
            startActivity(intent);
        } else if (position ==  2) {
            Intent intent = new Intent(getBaseContext(), MemoryCategoryActivity.class);
            startActivity(intent);
        }
    }
}
