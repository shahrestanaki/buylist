package com.tools;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
        LocalDate gregDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        com.github.mfathi91.time.PersianDate persianDate = com.github.mfathi91.time.PersianDate.fromGregorian(gregDate);
        return persianDate.toString().replaceAll("-", format);
    }

    public static String shamsiToMiladi(String shamsi, String formatSh, String formatEn) {
        String[] sp = shamsi.split(formatSh);
        com.github.mfathi91.time.PersianDate persianDate = com.github.mfathi91.time.PersianDate.of(Integer.valueOf(sp[0]), Integer.valueOf(sp[1]), Integer.valueOf(sp[2]));
        LocalDate gregDate1 = persianDate.toGregorian();

        return gregDate1.toString().replaceAll("-", formatEn);
    }

    public static Date changeDate(Date date, int number, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, number);
        return cal.getTime();
    }
}
