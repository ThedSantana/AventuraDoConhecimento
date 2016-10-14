package br.com.lealweb.aventuradoconhecimento;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;

/**
 * Created by leonardoleal on 02/09/16.
 */
public abstract class DefaultActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    ClickableScrollerViewPager.OnItemClickListener,
                    View.OnClickListener {

    protected  DrawerLayout drawer;
    private boolean playTutorial;

    protected abstract List<String> getTitles();
    protected abstract List<Integer> getBgRes();


    final String TUTORIAL_KEY = "tutorial";
    private ShowcaseView showcaseView;
    private ViewTarget target1;
    private ViewTarget target2;
    private ViewTarget target3;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupScreenParameters();
        setupElements();

        playTutorial();
    }

    private void setupScreenParameters() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setBackgroundDrawableResource(R.drawable.main_background);
    }

    private void setupElements() {
        setContentView(R.layout.activity_main);
        scrollableView();
        menu();
        floatingButton();
    }

    private void scrollableView() {
        ClickableScrollerViewPager viewPager = (ClickableScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        springIndicator.setViewPager(viewPager);

        viewPager.setOnItemClickListener(this);
    }

    private void menu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void floatingButton() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogleMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void toogleMenu() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_help) {
            setTutorialOn();
            playTutorial();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void playTutorial() {
        if (isTutorialOn()) {
            count = 0;

            target1 = new ViewTarget(R.id.fab, this);
            target2 = new ViewTarget(R.id.indicator, this);
            target3 = new ViewTarget(R.id.view_pager, this);

            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(Target.NONE)
                    .setContentTitle(getResources().getString(R.string.tutorial_intro))
                    .setShowcaseDrawer(new CustomShowcaseView(getResources()))
                    .setStyle(R.style.CustomShowcaseTheme)
                    .setOnClickListener(this)
                    .blockAllTouches()
                    .build();

            showcaseView.setButtonText(getResources().getString(R.string.next));
        }
    }

    @Override
    public void onClick(View v) {
        switch (count) {
            case 0:
                showcaseView.setTarget(target1);
                showcaseView.setContentTitle(getResources().getString(R.string.tutorial_step_1));
                showcaseView.setContentText(getResources().getString(R.string.tutorial_step_1_description));
                break;
            case 1:
                showcaseView.setTarget(target2);
                showcaseView.setContentTitle(getResources().getString(R.string.tutorial_step_2));
                showcaseView.setContentText(getResources().getString(R.string.tutorial_step_2_description));
                break;
            case 2:
                showcaseView.setTarget(target3);
                showcaseView.setContentTitle(getResources().getString(R.string.tutorial_step_3));
                showcaseView.setContentText(getResources().getString(R.string.tutorial_step_3_description));
                showcaseView.setButtonText(getResources().getString(R.string.understood));
                break;
            case 3:
                setTutorialOff();
                showcaseView.hide();
                break;
        }
        count++;
    }

    private void setTutorialOff() {
        getAppPreferences()
                .edit()
                .putBoolean(TUTORIAL_KEY, false)
                .commit();
    }

    private void setTutorialOn() {
        getAppPreferences()
                .edit()
                .putBoolean(TUTORIAL_KEY, true)
                .commit();
    }

    private SharedPreferences getAppPreferences() {
        return getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
    }

    private boolean isTutorialOn() {
        return getAppPreferences().getBoolean(TUTORIAL_KEY, true);
    }
}
