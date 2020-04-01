package com.doinb.utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    /*
     * FastDateFormat 多线程安全
     */
    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");

    /**
     * 传入月份区间, 推算日期
     *
     * @param month 增加的月份数
     * @return 日期格式 yyyyMMddHHmmss
     */
    public static String dayCalculate(int month) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 获取时区
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        String nowDate = FAST_DATE_FORMAT.format(calendar.getTime());
        return nowDate;
    }

    /**
     * 根据开始时间和结束时间计算天数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 天数
     * @throws ParseException 解析异常
     */
    public static int calcTotalDays(String startTime, String endTime) throws ParseException {
        Date date1 = null;
        Date date = FAST_DATE_FORMAT.parse(startTime);
        long ts = date.getTime();
        date1 = FAST_DATE_FORMAT.parse(endTime);
        long ts1 = date1.getTime();
        long ts2 = ts1 - ts;
        int totalDays = 0;
        totalDays = (int) (ts2 / (24 * 3600 * 1000) + 1);
        return totalDays;
    }

    public static int calcTotalDays(Date startTime, Date endTime) {
        long ts = startTime.getTime();
        long ts1 = endTime.getTime();
        long ts2 = ts1 - ts;
        int totalDays = 0;
        totalDays = (int) (ts2 / (24 * 3600 * 1000) + 1);
        return totalDays;
    }

    /**
     * 日期转换成字符串
     *
     * @param date 日期
     * @return yyyyMMddHHmmss格式的日期
     * @throws ParseException 异常
     */
    public static String dateToStr(Date date) {
        return FAST_DATE_FORMAT.format(date);
    }


    /**
     * 时间日期大小比较 compareTo()方法的返回值，beginDate小于endDate返回-1，date1大于date2返回1，相等返回0
     *
     * @param beginTime "2020-01-28 14:42:32";
     * @param endTime   "2020-02-20 10:20:00";
     * @return boolean
     */
    public static boolean compareDate(String beginTime, String endTime) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
        try {
            Date beginDate = fastDateFormat.parse(beginTime);
            Date endDate = fastDateFormat.parse(endTime);
            int compareTo = endDate.compareTo(beginDate);
            return compareTo >= 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 时间日期大小比较 compareTo()方法的返回值，beginDate小于endDate返回-1，date1大于date2返回1，相等返回0
     *
     * @param beginDate "2020-01-28 14:42:32";
     * @param endDate   "2020-02-20 10:20:00";
     * @return boolean
     */
    public static boolean compareDate(Date beginDate, Date endDate) {
        return beginDate.compareTo(endDate) == 0;
    }

    public static boolean isDate(String str) {
        FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
        try {
            formatter.parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * before()或者after()方法的返回值为boolean类型"
     *
     * @param beginTime "2020-01-28 14:42:32";
     * @param endTime   "2020-02-20 10:20:00";
     * @param pattern   yyyy-MM-dd HH:mm:ss"
     * @return boolean
     */
    public static boolean compareDate(String beginTime, String endTime, String pattern) {
        FastDateFormat format = FastDateFormat.getInstance(pattern);
        try {
            Date begin = format.parse(beginTime);
            Date end = format.parse(endTime);
            return begin.before(end);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 时间日期转换
     *
     * @param strDate 字符串yyyyMMddHHmmss
     * @return date
     */
    public static Date strToDateByPattern(String strDate, String pattern) {
        if (strDate == null || strDate.length() == 0) {
            return null;
        }
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        try {
            return fastDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 默认时区 2020-03-19T09:34:33.353+08:00[Asia/Shanghai]
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
    }
}
