package com.kmt.massage.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.kmt.massage.R;
import com.kmt.massage.data.model.MusicData;
import com.kmt.massage.data.model.VibPattern;
import com.kmt.massage.ui.base.BaseActivity;
import com.kmt.massage.ui.main.musicDialog.MusicListDialog;
import com.kmt.massage.utils.GridSpacingItemDecoration;
import com.kmt.massage.utils.MusicPlayerUtils;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kmt.massage.utils.ViewUtils.dpToPx;

public class MainActivity extends BaseActivity implements MainMvpView, MenuAdapter.Callback, DialogCommunicator {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    MenuAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.layout_content)
    View layout_content;

    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.fab)
    ImageView ivStart;

    @BindView(R.id.rv_application)
    RecyclerView mRecyclerView;
    Vibrator vibrator;
    AnimationDrawable mAnimation;
    List<VibPattern> patterns = new ArrayList<>();
    List<MusicData> musicList = new ArrayList<>();
    private boolean isVibrating = false;
    private String selectedMusic = "";
    private long[] selectPattern;
    private boolean isMusicOn = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mAdapter.setCallback(this);

        setUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @SuppressWarnings({"unused", "RedundantSuppression", "EmptyMethod"})
    @Override
    public void onFragmentAttached() {
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupMenuRecyclerView();
        prepareList();
        prepareMusicList();
        if (patterns.size() > 0)
            selectPattern = patterns.get(0).pattern;

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        final RippleBackground rippleBackground = findViewById(R.id.content);
        ivStart.setOnClickListener(view -> {
            if (selectPattern != null) {
                isVibrating = !isVibrating;
                if (isVibrating) {
                    if (!TextUtils.isEmpty(selectedMusic) && isMusicOn) {
                        MusicPlayerUtils.playSound(MainActivity.this, selectedMusic);
                    }
                    rippleBackground.startRippleAnimation();
                    ivStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    vibrator.vibrate(selectPattern, 0); // repeats forever
                } else {
                    if (isMusicOn)
                        MusicPlayerUtils.stopSound();
                    rippleBackground.stopRippleAnimation();
                    ivStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                    vibrator.cancel();
                }
            }
        });
    }

    private void setupMenuRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void prepareList() {
        VibPattern pattern = new VibPattern();
        pattern.title = "Blast";
        pattern.pic = R.drawable.blast;
        pattern.pattern = new long[]{0, 30, 50, 30, 50, 30, 50, 30, 50, 30, 50};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Burst";
        pattern.pic = R.drawable.burst;
        pattern.pattern = new long[]{0, 30, 30, 30, 30, 30, 30, 30, 20, 30, 20};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Plain";
        pattern.pic = R.drawable.plain;
        pattern.pattern = new long[]{0, 50, 300, 50, 300};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Throb";
        pattern.pic = R.drawable.throb;
        pattern.pattern = new long[]{0, 30, 200, 30, 150, 30, 100, 30, 150, 30, 200};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Caress";
        pattern.pic = R.drawable.caress;
        pattern.pattern = new long[]{0, 30, 20, 30, 20, 30, 20, 30, 20, 30, 20};
        patterns.add(pattern);


        pattern = new VibPattern();
        pattern.title = "Fondle";
        pattern.pic = R.drawable.fondle;
        pattern.pattern = new long[]{0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 200, 50, 200};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Ecstaz";
        pattern.pic = R.drawable.ecstaz;
        pattern.pattern = new long[]{0, 30, 20, 30, 20, 30, 20, 30, 20, 30, 20, 30, 300, 30, 30, 30, 30};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Pulse";
        pattern.pic = R.drawable.pulse;
        pattern.pattern = new long[]{0, 100, 25, 5, 100};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Engine";
        pattern.pic = R.drawable.engine;
        pattern.pattern = new long[]{0, 30, 30, 30, 50, 30, 50, 30, 60, 30, 100};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Heavy Blast";
        pattern.pic = R.drawable.heavy_blast;
        pattern.pattern = new long[]{0, 30, 100, 30, 100, 30, 100, 30, 100, 30, 100};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Volcano";
        pattern.pic = R.drawable.volcano;
        pattern.pattern = new long[]{0, 30, 200, 30, 200, 30, 75, 20, 75, 20, 75};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Tornado";
        pattern.pic = R.drawable.tornado;
        pattern.pattern = new long[]{0, 30, 400, 30, 400, 30, 100, 30, 100, 30, 100, 30, 100};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Rainy";
        pattern.pic = R.drawable.rainy;
        pattern.pattern = new long[]{0, 30, 100, 30, 100, 30, 100, 20, 200, 30, 200, 30, 200, 30, 400};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Rainy";
        pattern.pic = R.drawable.rainy;
        pattern.pattern = new long[]{0, 30, 100, 30, 100, 30, 100, 20, 200, 30, 200, 30, 200, 30, 400};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Waterfall";
        pattern.pic = R.drawable.waterfall;
        pattern.pattern = new long[]{0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 400};
        patterns.add(pattern);

        pattern = new VibPattern();
        pattern.title = "Wet";
        pattern.pic = R.drawable.wet;
        pattern.pattern = new long[]{0, 30, 30, 30, 30, 30, 50, 30, 200, 30, 30, 30, 30, 30, 400};
        patterns.add(pattern);

        mAdapter.addItems(patterns);

    }

    public void prepareMusicList() {
        MusicData musicData = new MusicData();
        musicData.title = "Loon";
        musicData.fileName = "loon.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Drone";
        musicData.fileName = "drone.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Artefact";
        musicData.fileName = "artefact.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Drum";
        musicData.fileName = "drum.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Meditation";
        musicData.fileName = "meditation.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Ringing";
        musicData.fileName = "ringing.mp3";
        musicList.add(musicData);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @Override
    public void onItemClicked(VibPattern application) {
        selectPattern = application.pattern;
//        showMessage(application.title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        if (item.getItemId() == R.id.menu_music) {
            MusicListDialog.newInstance(musicList).show(getSupportFragmentManager(), MusicListDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setSelectedMusic(String selectedMusic) {
        this.selectedMusic = selectedMusic;
    }

    @Override
    public void setMusicOnOff(boolean isPlaying) {
        isMusicOn = isPlaying;
    }
}
