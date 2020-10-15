package com.kmt.massage.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.kmt.massage.di.ApplicationContext;
import com.kmt.massage.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_KEY_CURRENT_LANGUAGE = "PREF_KEY_CURRENT_LANGUAGE";
    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentLanguage() {
        return mPrefs.getString(PREF_KEY_CURRENT_LANGUAGE, "en");
    }

    @Override
    public void setCurrentLanguage(String language) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_LANGUAGE, language).apply();
    }
}
