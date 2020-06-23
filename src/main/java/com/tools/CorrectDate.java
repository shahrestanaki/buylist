package com.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CorrectDate {
    public static String dateTimeZone(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        return f.format(date);
    }

    public static Date addToDate(Date date, long millis) {
        Calendar timeout = Calendar.getInstance();
        timeout.setTimeInMillis(date.getTime() + (millis * 1000));
        return timeout.getTime();
    }

    public static String convertDate(Date date, String format) {
        return new SimpleDateFormat("yyyy" + format + "MM" + format + "dd").format(date);
    }

    public static String miladiToShamsi(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) + format + (cal.get(Calendar.MONTH) + 1) + format + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String shamsiToMiladi(String shamsi) {
        Locale locale = new Locale("fa");
        Calendar calendar = Calendar.getInstance(locale);
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);

        return df.format(calendar);
    }
}
