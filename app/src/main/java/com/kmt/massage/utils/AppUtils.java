package com.kmt.massage.utils;

import android.content.Context;

import java.text.DecimalFormat;

import kotlin.jvm.JvmStatic;

@SuppressWarnings({"unused", "RedundantSuppression"})
public final class AppUtils {

    // file picker constants
    public static final String EXTRA_FILENAME = "com.pixel.bits.file_provider";
    public static final int CAPTURE_PICTURE_REQUEST_CODE = 12;
    public static final int PICK_PHOTO_REQUEST_CODE = 13;
    private static final DecimalFormat format = new DecimalFormat("#.##");

    private static final long MiB = 1024 * 1024;
    private static final long KiB = 1024;

    private AppUtils() {
    }

//    fun getCurrentTheme(context:Context?): Int {
//        return getDefaultSharedPreferences(context).getInt(Constant.PREFERENCE_THEME, 0)
//    }
//
//    fun setCurrentTheme(context: Context?, theme: Int) {
//        getDefaultSharedPreferences(context).edit().putInt(Constant.PREFERENCE_THEME, theme).apply()
//    }

}
