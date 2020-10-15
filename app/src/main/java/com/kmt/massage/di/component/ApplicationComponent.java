package com.kmt.massage.di.component;

import android.app.Application;
import android.content.Context;

import com.kmt.massage.MvpApp;
import com.kmt.massage.data.DataManager;
import com.kmt.massage.di.ApplicationContext;
import com.kmt.massage.di.module.ApplicationModule;
import com.kmt.massage.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

@SuppressWarnings({"unused", "RedundantSuppression"})
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}