package com.jusfoun.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String TYPE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String TYPE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String TYPE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String TYPE_YYYY_MM = "yyyy-MM";
    public static final String TYPE_HH_MM = "HH:mm";

    public static final String TYPE_YYYY_MM_DD_HH_MM_SS2 = "yyyy.MM.dd HH:mm:ss";
    public static final String TYPE_YYYY_MM_DD_HH_MM2 = "yyyy.MM.dd HH:mm";
    public static final String TYPE_YYYY_MM2 = "yyyy.MM";
    public static final String TYPE_YYYY_MM_DD2 = "yyyy.MM.dd";

    /**
     * 获取今年的年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当年的月份，如果是1月份，返回1
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }


    /**
     * 获取今天是几号
     *
     * @return
     */
    public static int getTodayDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获得指定日期的前一天
     *
     * @param day
     * @param format 例：yyyy-MM-dd
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayBefore(String day, String format) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        return new SimpleDateFormat(format).format(c.getTime());
    }


    /**
     * 获得指定日期的后一天
     *
     * @param day
     * @param format 例：yyyy-MM-dd
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayAfter(String day, String format) {
        Calendar c = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat(format).parse(day);
            c.setTime(date);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }

    /**
     * 返回星期几
     *
     * @param time
     * @param format "EEEE" 返回格式为 "星期一、、星期日"  "EE" 返回格式为"周一、、、周日"
     * @return
     */
    public static String getWeek(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * 返回星期几,"周一、、、周日"
     *
     * @param time
     * @return
     */
    public static String getWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        return sdf.format(calendar.getTime());
    }

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String format(String date, String sourceFormat, String targetFormat) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(sourceFormat).parse(date));
            return new SimpleDateFormat(targetFormat).format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(String date, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(format).parse(date));
            return new SimpleDateFormat(format).format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getMillisecond(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取该月有多少天
     *
     * @param year
     * @param month 如果是查7月， 传值为7
     * @return
     */
    public static int getDayNum(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//7月
        return cal.getActualMaximum(Calendar.DATE);
    }


    /**
     * 和当前时间比较
     * 1）1分钟以内 显示 : 刚刚
     * 2）1小时以内 显示 : X分钟前
     * 3）今天或者昨天 显示 : 今天 09:30 昨天 09:30
     * 4) 今年 显示 : 09-12
     * 5) 大于本年 显示 : 2016-09-09
     *
     * @return
     */
    private static SimpleDateFormat sdfMd = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat sdfYMd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfHm = new SimpleDateFormat("HH:mm");

    /**
     * 判断时间距离当前的时间
     *
     * @param time
     * @param sdf
     * @return
     */
    public static String parseTime(String time, SimpleDateFormat sdf) {
        try {
            return parseTime(sdf.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 判断时间距离当前的时间
     *
     * @param time
     * @return
     */
    public static String parseTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        long dex = (System.currentTimeMillis() - calendar.getTimeInMillis()) / 1000;
        if (dex <= 60)
            return "刚刚";
        else if (dex <= 60 * 60)
            return (dex / 60) + "分钟前";
        else if (Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && Calendar.getInstance().get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            return "今天" + sdfHm.format(calendar.getTime());
        else if (Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && Calendar.getInstance().get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && yesterday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            return "昨天" + sdfHm.format(calendar.getTime());
        else if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
            return sdfMd.format(calendar.getTime());
        else
            return sdfYMd.format(calendar.getTime());
    }
}