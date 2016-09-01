package br.com.lealweb.aventuradoconhecimento;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;


public class MathCategoryActivity extends ActionBarActivity {

    ClickableScrollerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        viewPager = (ClickableScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
        setupParameters();

        viewPager.setOnItemClickListener(new MyOnItemClickListener());

    }

    private void setupParameters() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private List<String> getTitles(){
        return Lists.newArrayList("☆", "☆", "☆", "☆");
    }

    private List<Integer> getBgRes(){
        return Lists.newArrayList(R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private Context getContext() {
        return this;
    }
    private class MyOnItemClickListener implements ClickableScrollerViewPager.OnItemClickListener {

        @Override
        public void onItemClick(int position, MotionEvent e) {
            if (position ==  0) {
                Toast.makeText(getContext(), "Jogo de Preencher Números", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),
                        br.com.lealweb.aventuradoconhecimento.jogopreenchernumeros.GameActivity.class);
                startActivity(intent);
            }
        }
    }
}
