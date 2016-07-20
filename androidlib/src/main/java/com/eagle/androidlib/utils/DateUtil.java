package com.eagle.androidlib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/18 0018.
 */
public class DateUtil {

    public static String deaultDateFormat = "yyyy-MM-dd";
    public static String deaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDateStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(deaultDateFormat);
        return simpleDateFormat.format(getCurrentDate());
    }

    public static String getCurrentDateTimeStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(deaultDateTimeFormat);
        return simpleDateFormat.format(getCurrentDate());
    }
    public static Date getCurrentDate(){
        return new Date();
    }

    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMaxDay(){
        return Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date parseDateTime(String str){
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(deaultDateTimeFormat);
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDateTime(long s){
        return new Date(s);
    }

    public static int getHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getMinute(long s){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDateTime(s));
        return calendar.get(Calendar.MINUTE);
    }

    public static int parseToMinute(long s){
        return (int) (s/1000/60);
    }
}
