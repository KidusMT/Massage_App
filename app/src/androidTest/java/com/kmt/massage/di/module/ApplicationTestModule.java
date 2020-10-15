package com.kmt.massage.di.module;

import android.app.Application;
import android.content.Context;

import com.kmt.massage.BuildConfig;
import com.kmt.massage.R;
import com.kmt.massage.data.AppDataManager;
import com.kmt.massage.data.DataManager;
import com.kmt.massage.data.db.AppDbHelper;
import com.kmt.massage.data.db.DbHelper;
import com.kmt.massage.data.network.ApiHelper;
import com.kmt.massage.data.network.AppApiHelper;
import com.kmt.massage.data.prefs.AppPreferencesHelper;
import com.kmt.massage.data.prefs.PreferencesHelper;
import com.kmt.massage.di.ApiInfo;
import com.kmt.massage.di.ApplicationContext;
import com.kmt.massage.di.DatabaseInfo;
import com.kmt.massage.di.PreferenceInfo;
import com.kmt.massage.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@SuppressWarnings({"unused", "RedundantSuppression", "EmptyMethod", "SameReturnValue"})
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    // TODO : Mock all below for UI testing

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

//    @Provides
//    @Singleton
//    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
//                                                           PreferencesHelper preferencesHelper) {
//        return new ApiHeader.ProtectedApiHeader(
//                apiKey,
//                preferencesHelper.getCurrentUserId(),
//                preferencesHelper.getAccessToken());
//    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("seed/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
