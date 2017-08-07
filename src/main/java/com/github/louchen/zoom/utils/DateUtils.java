package com.github.louchen.zoom.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utils - 日期
 *
 * @author louchen
 */
@Slf4j
public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD hh:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    public static int compareDate(Date s, Date e, boolean isOnlyDate) {
        if (s == null && e == null) {
            return 0;
        } else if (s == null) {
            return -1;
        } else if (e == null) {
            return 1;
        } else {
            return isOnlyDate ? getDate(s).compareTo(getDate(e)) : s.compareTo(e);
        }
    }

    private final static SimpleDateFormat getFormat(String pattern, Locale locale) {
        return new SimpleDateFormat(pattern, locale);
    }

    /**
     * format 日期类型 格式化成字符串类型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return format(date, pattern, Locale.getDefault());
    }

    /**
     * format 日期类型 格式化成字符串类型
     *
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static String format(Date date, String pattern, Locale locale) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isNotBlank(pattern)) {
            String format = getFormat(pattern, locale).format(date);
            return format;
        }
        throw new IllegalArgumentException("param pattern can not be null");
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            log.error("", e);
            return null;
        }
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date, String pattern) {
        DateFormat fmt = new SimpleDateFormat(pattern);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long aa = 0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            log.error("", e);
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }


    /**
     * 判断是否是当天
     *
     * @return
     * @throws ParseException
     */
    public static boolean isCurrentDay(String date_str) throws ParseException {
        Date current_day = new Date();
        Date date = sdfTime.parse(date_str);
        date_str = sdfTime.format(date);
        String current_str = sdfTime.format(current_day);

        if (current_str.substring(0, 10).equals(date_str.substring(0, 10))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 增加或减少指定数量的时间
     *
     * @param date   时间
     * @param field  计算域
     * @param amount 数值
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 增加或减少指定天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 增加或减少指定分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToSimpleString(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    /**
     * 当前日期 指定天数之前的日期
     *
     * @param days
     * @return
     */
    public static String laterDate(int days) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp_date = null;
        try {
            Date d = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.DATE, -days);
            temp_date = c.getTime();
        } catch (Exception e) {
            log.error("", e);
        }
        return format.format(temp_date);
    }

    public static String nextDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp_date = null;
        try {
            Date d = format.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.DATE, +1);
            temp_date = c.getTime();
        } catch (Exception e) {
            log.error("", e);
        }
        return format.format(temp_date);
    }

    public static Date string2Date(String date) {
        Date temp_date = null;
        if (date != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                temp_date = format.parse(date);
            } catch (ParseException e) {
                log.error("", e);
            }
        } else {

        }
        return temp_date;
    }


    public static Date string2SimpleDate(String date) {
        Date temp_date = null;
        if (date != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                temp_date = format.parse(date);
            } catch (ParseException e) {
                log.error("", e);
            }
        } else {

        }
        return temp_date;
    }

    /**
     * @return 当前时间(以秒为单位)
     */
    public static int getCurrentTime() {
        return new Long(System.currentTimeMillis() / 1000).intValue();
    }

    public static Date string2Date(String date, String pattern) {
        Date temp_date = null;
        if (date != null) {
            DateFormat format = new SimpleDateFormat(pattern);
            try {
                temp_date = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp_date;
    }

    /**
     * 日期转字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return "";
    }

    public static long formatTime(String times) {
        if (StringUtils.isEmpty(times))
            return (new Date()).getTime() / 1000;

        Date date = string2Date(times, "yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return 0;
        return date.getTime() / 1000;
    }

    public static Date getWeekDay(int weekday) {
        return getWeekDayCal(weekday).getTime();
    }

    public static Calendar getWeekDayCal(int weekday) {
        //周一配送单   周五16点之后下下周一
        Calendar cal = Calendar.getInstance();
        Calendar timeNow = Calendar.getInstance();
        int n = 0;
        long timeNowss, fridayss;
        switch (weekday) {
            case 1:
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                timeNowss = Long.valueOf(dateToString(timeNow.getTime(), "yyyy-MM-dd HH:mm:ss").replaceAll("[-\\s:]", ""));
                fridayss = Long.valueOf(dateToString(cal.getTime(), "yyyy-MM-dd 11:00:00").replaceAll("[-\\s:]", ""));
                n = timeNowss > fridayss ? 2 : 1;
                cal.add(Calendar.DATE, n * 7);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 6:
                //周六配送单   周三16点之后下周六
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                timeNowss = Long.valueOf(dateToString(timeNow.getTime(), "yyyy-MM-dd HH:mm:ss").replaceAll("[-\\s:]", ""));
                fridayss = Long.valueOf(dateToString(cal.getTime(), "yyyy-MM-dd 11:00:00").replaceAll("[-\\s:]", ""));
                n = timeNowss > fridayss ? 1 : 0;
                cal.add(Calendar.DATE, n * 7);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;

        }
        return cal;
    }

    /**
     *  是否是当前时间
     *
     * @param dateSelected
     * @return
     */
    public static int compareWithCurrentDate(String dateSelected) {
        Date d1 = getDate(new Date());
        Date d2 = getDate(string2Date(dateSelected, "yyyy-MM-dd"));
        if (d2 == null)
            return -1;
        return d2.compareTo(d1);
    }

    public static Date getDate(Date date) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得指定时间当天 00:00:00 的Date对象
     *
     * @param date 指定时间
     * @return 结果
     */
    public static Date dayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得指定时间当天 23:59:59 的Date对象
     *
     * @param date 指定时间
     * @return 结果
     */
    public static Date dayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获得中文周N
     *
     * @return 结果
     */
    public static String weekDayInChinese() {
        return weekDayInChinese(new Date());
    }

    /**
     * 根据指定日期获得中文周N
     *
     * @param date 指定日期
     * @return 结果
     */
    public static String weekDayInChinese(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(GregorianCalendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "";
        }
    }

    /**
     * 根据指定日期获得中文数字周N
     *
     * @param date 指定日期
     * @return 结果
     */
    public static Integer weekDayInChineseNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(GregorianCalendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                return 0;
        }
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date getNow() {
        return new Date();
    }

}