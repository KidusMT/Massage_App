package com.kmt.massage;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDex;

import com.kmt.massage.data.DataManager;
import com.kmt.massage.di.component.ApplicationComponent;
import com.kmt.massage.di.component.DaggerApplicationComponent;
import com.kmt.massage.di.module.ApplicationModule;
import com.kmt.massage.utils.AppLogger;

import javax.inject.Inject;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class MvpApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @Inject
    DataManager mDataManager;
    private ApplicationComponent mApplicationComponent;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;

    private static MvpApp singleton = null;

    public static MvpApp getInstance() {

        if(singleton == null)
        {
            singleton = new MvpApp();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        MultiDex.install(mContext);
        singleton = this;

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        this.isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false);
    }

    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }

    public void setIsNightModeEnabled(boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
