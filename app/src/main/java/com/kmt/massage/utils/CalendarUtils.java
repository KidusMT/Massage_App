package com.kmt.massage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class CalendarUtils {

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(date);
    }
}
