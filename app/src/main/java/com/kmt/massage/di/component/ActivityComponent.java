package com.kmt.massage.di.component;

import com.kmt.massage.di.PerActivity;
import com.kmt.massage.di.module.ActivityModule;
import com.kmt.massage.ui.main.MainActivity;
import com.kmt.massage.ui.main.MenuAdapter;
import com.kmt.massage.ui.main.musicDialog.MusicAdapter;
import com.kmt.massage.ui.main.musicDialog.MusicListDialog;
import com.kmt.massage.ui.splash.SplashActivity;

import dagger.Component;

@SuppressWarnings({"unused", "RedundantSuppression"})
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SplashActivity activity);

    void inject(MenuAdapter adapter);

    void inject(MusicListDialog dialog);

    void inject(MusicAdapter adapter);
}
