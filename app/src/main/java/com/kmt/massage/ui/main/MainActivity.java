package com.kmt.massage.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.kmt.massage.MvpApp;
import com.kmt.massage.R;
import com.kmt.massage.data.model.MusicData;
import com.kmt.massage.data.model.VibPattern;
import com.kmt.massage.ui.base.BaseActivity;
import com.kmt.massage.ui.main.musicDialog.MusicListDialog;
import com.kmt.massage.utils.AppLogger;
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

    @BindView(R.id.start)
    ImageView ivStart;

    @BindView(R.id.rv_application)
    RecyclerView mRecyclerView;
    Vibrator vibrator;
    List<VibPattern> patterns = new ArrayList<>();
    List<MusicData> musicList = new ArrayList<>();
    private boolean isVibrating = false;
    private String selectedMusic = "";
    private long[] selectPattern;
    private boolean isMusicOn = true;
    private boolean isNightMode;
    private MenuItem nightMode;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MvpApp.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mAdapter.setCallback(this);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            isNightMode = true;

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
                    startMusic(selectedMusic);
                    rippleBackground.startRippleAnimation();
                    ivStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    startVibration(selectPattern);
                } else {
                    stopMusic();
                    rippleBackground.stopRippleAnimation();
                    ivStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                    stopVibration();
                }
            }
        });
    }

    private void startMusic(String selectedMusic) {
        if (!TextUtils.isEmpty(selectedMusic) && isMusicOn && isVibrating) {
            stopMusic();
            MusicPlayerUtils.playSound(MainActivity.this, selectedMusic);
        }
    }

    private void stopMusic() {
        if (isMusicOn)
            MusicPlayerUtils.stopSound();
    }

    private void startVibration(long[] selectPattern) {
        if (isVibrating)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(selectPattern, 0));
            } else {
                //deprecated in API 26
                vibrator.vibrate(selectPattern, 0); // repeats forever
            }
    }

    private void stopVibration() {
        vibrator.cancel();
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
        pattern.title = "Storm";
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
        musicData.title = "Bird";
        musicData.fileName = "bird.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Night";
        musicData.fileName = "night.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Rain";
        musicData.fileName = "rain.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Sea";
        musicData.fileName = "sea.mp3";
        musicList.add(musicData);

        musicData = new MusicData();
        musicData.title = "Wind";
        musicData.fileName = "wind.mp3";
        musicList.add(musicData);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @Override
    public void onItemClicked(VibPattern application) {
        selectPattern = application.pattern;
        startVibration(selectPattern);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        nightMode = menu.findItem(R.id.menu_mode);
        if (isNightMode) nightMode.setIcon(R.drawable.ic_light_theme);
        else nightMode.setIcon(R.drawable.ic_night_theme);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        if (item.getItemId() == R.id.menu_mode) {
            isNightMode = !isNightMode;
            if (isNightMode) {
                MvpApp.getInstance().setIsNightModeEnabled(true);
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            } else {
                MvpApp.getInstance().setIsNightModeEnabled(false);
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        }

        if (item.getItemId() == R.id.menu_music) {
            MusicListDialog.newInstance(musicList, isMusicOn).show(getSupportFragmentManager(), MusicListDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setSelectedMusic(String selectedMusic) {
        this.selectedMusic = selectedMusic;
        startMusic(selectedMusic);
    }

    @Override
    public void setMusicOnOff(boolean isPlaying) {
        isMusicOn = isPlaying;
        if (!isMusicOn) MusicPlayerUtils.stopSound();
        else startMusic(selectedMusic);
    }
}
