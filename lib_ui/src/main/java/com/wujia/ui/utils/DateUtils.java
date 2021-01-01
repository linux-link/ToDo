package com.wujia.ui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_1SS = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_1SSS = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_2 = "MM月dd日";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_4 = "yyyy年MM月dd日";
    public static final String HH_MM = "HH:mm";
    public static final String MM = "mm";
    public static final String SS = "ss";
    public static final String HH = "HH";
    public static final String DATE_FORMAT_5S = "HH:mm:ss";
    public static final String DATE_FORMAT_6 = "MM-dd";
    public static final String DATE_FORMAT_7 = "MM-dd HH:mm";
    public static final String DATE_FORMAT_8 = "yyyy年MM月dd日 HH:mm";


    public static String showDateTime(String publishTime) {
        String textTime = "";
        Date date = stringToDate(publishTime, "yyyy-MM-dd HH:mm:ss");
        long time = date.getTime();
        long currentTime = System.currentTimeMillis();
        long s = 1000;
        long min = 60 * s;
        long hour = 60 * min;
        long day = 24 * hour;
        long year = 365 * day;
        if (currentTime - time < min) {
            return textTime = "刚刚";
        }
        if (currentTime - time < hour) {
            int mmin = (int) ((currentTime - time) / min);
            return textTime = mmin + "分钟前";
        }
        if (currentTime - time < day) {
            int mhour = (int) ((currentTime - time) / hour);
            return textTime = mhour + "小时前";
        }

        String years = dateToString(date, "yyyy");
        String currentYear = currentDateTime("yyyy");

        if (currentTime - time < year && years.equals(currentYear)) {
            return textTime = dateToString(date, "MM月dd日 HH:mm");
        }

        textTime = dateToString(date, "yyyy年MM月dd日");
        return textTime;
    }

    /**
     * 获取当前时间(字符形式)
     *
     * @param formats 格式
     * @return
     */
    public static String currentDateTime(String formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats, Locale.getDefault());
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 获取当前日期
     *
     * @param format
     * @return
     */
    public static String getCurrenDateString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * Date转字符串
     *
     * @param date
     * @param formats
     * @return
     */
    public static String dateToString(Date date, String formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 比较当前时间是否大于输入时间
     *
     * @param time
     * @param format
     * @return
     */
    public static boolean comparisonTime(String time, String format) {
        Date date = stringToDate(time, format);
        long times = date2millonSenconds2(date);
        String currentTime = getCurrenDateString(format);
        Date currentDate = stringToDate(currentTime, format);
        long currentTimes = date2millonSenconds2(currentDate);

        if (currentTimes >= times) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 字符串转Date
     *
     * @param time
     * @param formats
     * @return
     */
    public static Date stringToDate(String time, String formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * 毫秒转日期(较简洁)
     *
     * @param millons
     * @return
     */
    public Date millonSenconds2Date(long millons) {
        // 方法一
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(millons);
        return ca.getTime();
    }

    public static Date millonSenconds2DateStatic(long millons) {
        // 方法一
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(millons);
        return ca.getTime();
    }

    /**
     * 毫秒转日期方法二
     *
     * @param millons
     * @return
     */
    public Date millonSenconds2Date2(long millons) {
        Date dat = new Date(millons);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        return gc.getTime();
    }

    /**
     * 日期转毫秒
     *
     * @param date
     * @return
     */
    public long date2millonSenconds(Date date) {
        return date.getTime();
    }

    public static long date2millonSenconds2(Date date) {
        return date.getTime();
    }

    public static long formatHourToMin(String hour) {
        Date date = new Date(hour);
        return date.getTime();
    }

    /**
     * 格式化时长
     *
     * @param time
     * @return
     */
    public static String formatTimeLegth(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        // 时长不足1小时
        if (hour == 00) {
            if (minute == 00) {
                return String.format("%02d秒", second);
            } else {
                if (second == 00) {
                    return String.format("%2d分钟", minute);
                } else {
                    return String.format("%2d分%02d秒", minute, second);
                }

            }

        } else {
            return String.format("%2d小时%2d分钟", hour, minute);
        }

    }

    /**
     * 格式化时长
     *
     * @param time
     * @return
     */
    public static String formatFloatTimeLegth(double time) {

        int minute = (int) (time / 60);
        int hour = minute / 60;
        int second = (int) (time % 60);
        minute %= 60;
        // 时长不足1小时
        if (hour == 00) {
            if (minute == 00) {
                return String.format("%2d秒", second);
            } else {
                if (second == 00) {
                    return String.format("%2d分钟", minute);
                } else {
                    return String.format("%2d分%2d秒", minute, second);
                }

            }

        } else {
            return String.format("%2d小时%2d分钟", hour, minute);
        }

    }

    public static String formatFloatTimeLegth(long time) {

        int minute = (int) (time / 60);
        int hour = minute / 60;
        int second = (int) (time % 60);
        minute %= 60;
        // 时长不足1小时
        if (hour == 00) {
            if (minute == 00) {
                return String.format("%2d秒", second);
            } else {
                if (second == 00) {
                    return String.format("%2d分钟", minute);
                } else {
                    return String.format("%2d分%2d秒", minute, second);
                }

            }

        } else {
            return String.format("%2d小时%2d分钟", hour, minute);
        }

    }

    /**
     * 格式化视频时长
     *
     * @param time
     * @return
     */
    public static long formatVideoTimeLength(String time) {
        String[] kl;
        if (time.contains(".")) {
            kl = time.split("\\.")[0].split(":");
        } else {
            kl = time.split(":");
        }
        long millTime = 0L;
        if (Integer.parseInt(kl[0]) > 0) {
            millTime += Integer.parseInt(kl[0]) * 3600;
        }
        if (Integer.parseInt(kl[1]) > 0) {
            millTime += Integer.parseInt(kl[1]) * 60;
        }
        if (Integer.parseInt(kl[2]) > 0) {
            millTime += Integer.parseInt(kl[2]);
        }
        return millTime;
    }

    /**
     * 时间转换
     *
     * @param time
     */
    public static String toTime(int time, boolean isVideo) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        if (isVideo) {
            // 时长不足1小时
            if (hour == 00)
                return String.format("%02d:%02d", minute, second);
            else
                return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }

    }


    /**
     * 日期转化为时间戳
     **/
    public static long getDateToTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = simpleDateFormat.parse(time);
        long timeStemp = date.getTime();

        return timeStemp;
    }

    /**
     * 日期转化为时间戳
     **/
    public static long getDateToDate(String time, String dateFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date date = simpleDateFormat.parse(time);
        long timeStemp = date.getTime();

        return timeStemp;
    }


    /**
     * 日期转化为时间戳
     **/
    public static long getHourToTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = simpleDateFormat.parse(time);
        long timeStemp = date.getTime();

        return timeStemp;
    }

    /**
     * 字符串时间转其他格式
     *
     * @param time
     * @param inFormat
     * @param outFormat
     * @return
     */
    public static String stampToDateString(String time, String inFormat, String outFormat) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inFormat, Locale.getDefault());
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(outFormat, Locale.getDefault());
        Date dates = new Date(date.getTime());
        res = dateFormat.format(dates);
        return res;
    }

    /**
     * 根据日期取得星期几
     *
     * @param time 日期
     * @return 周几
     */
    public static String getWeek(String time) {
        Date date = stringToDate(time, YYYY_MM_DD_HH_MM_SS);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date);
    }

    public static String getWeek(String time, String inFormat) {
        Date date = stringToDate(time, inFormat);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date);
    }

    public static String getFormatTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    public static final String[] sWeek = new String[]
            {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    public static String getWeek(int weekday) {
        int weekly = weekday - 1 > 0 ? weekday - 1 : 7;
        return sWeek[weekly - 1];
    }
}
