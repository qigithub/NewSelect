package com.qi.newselect.utils;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by dongqi on 2016/12/16.
 */
public class TimeUtils {
    private static String[] weekDaysName = { "Sunday", "Monday",
            "Tuesday", "Wednesday",
            "Thursday", "Friday",
            "Saturday"};
    private String[] weekDaysCode = { "7", "1", "2", "3", "4", "5", "6" };
    public static void initTimeCls() {
        GregorianCalendar time = new GregorianCalendar(TimeZone.getTimeZone("GMT-8:00"));


    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getDate2WeekStr(Context ctx, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }


    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);//2016
        a.set(Calendar.MONTH, month - 1);//month
        a.set(Calendar.DATE, 1);// 1 号
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

}
