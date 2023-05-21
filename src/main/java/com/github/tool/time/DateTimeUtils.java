package com.github.tool.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * Java8日期工具类
 */
public class DateTimeUtils {

    /**
     * test
     */
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println("当前时间 >> " + time);
        System.out.println("获取上一个月的第一天 >> " + beforeMonthFirstDay(time));
        System.out.println("获取上一个月的最后一天 >> " + beforeMonthLastDay(time));

        // 2021-09-20 00:00:00
        //time = 1632067200000L;
        System.out.println("获取上周一 >> " + beforeWeekFirstDay(time));
        System.out.println("获取上周日 >> " + beforeWeekLastDay(time));
        System.out.println(formatterToUTCTimeZone(time, "+08:00", "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * UTC时间的ISO日期格式
     */
    public static final String YYYY_MM_DD_T_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 设置时间戳的毫秒位为:0毫秒
     *
     * @param time 时间
     * @return 时间戳
     */
    public static Long timeInMilliFromZero(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime.withNano(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取上一个月的第一天 00:00:00 000
     * <p>
     * 月结
     *
     * @return 时间戳
     */
    public static Long beforeMonthFirstDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .plusMonths(-1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取上一个月的最后一天 23:59:59 999
     * <p>
     * 月结/半月结
     *
     * @return 时间戳
     */
    public static Long beforeMonthLastDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .plusMonths(-1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 判断当前时间 日 是否小于15号，
     * <p>
     * 半月结
     *
     * @return true: 如果小于等于15号则计算16-月底
     * false: 如果大于15号则计算1-15
     */
    public static boolean isLessOrEqualFifteen(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime.getDayOfMonth() <= 15;
    }

    /**
     * 获取本月的第一天 00:00:00 000
     * <p>
     * 半月结
     *
     * @return 时间戳
     */
    public static Long monthFirstDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取本月的15号 23:59:59 999
     * <p>
     * 半月结
     *
     * @return 时间戳
     */
    public static Long monthFifteenDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .withDayOfMonth(15)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取上一个月的16号 00:00:00 000
     * <p>
     * 半月结
     *
     * @return 时间戳
     */
    public static Long beforeMonthSixteenDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .plusMonths(-1)
                .withDayOfMonth(16)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取上周一 00:00:00 000
     * <p>
     * 周结
     *
     * @return 时间戳
     */
    public static Long beforeWeekFirstDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
                .plusDays(-6)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取上周日 23:59:59 999
     * <p>
     * 周结
     *
     * @return 时间戳
     */
    public static Long beforeWeekLastDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime
                .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 获得当前时间戳的毫秒位为:0毫秒
     *
     * @return 时间戳
     */
    public static Long nowTimeInMilliFromZero() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.withNano(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当天的00:00:00 000
     *
     * @param time 时间
     * @return 时间戳
     */
    public static Long startOfDay(Long time) {
        return startOfDay(new Date(time)).getTime();
    }

    /**
     * 获取当天的23:59:59 999
     *
     * @param time 时间
     * @return 时间戳
     */
    public static Long endOfDay(Long time) {
        return endOfDay(new Date(time)).getTime();
    }

    /**
     * 获取当天的23：59：59 + 指定年数
     *
     * @param yearsToAdd 时间
     * @return 时间戳
     */
    public static Long plusYears(int yearsToAdd) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return endOfDay.plusYears(yearsToAdd).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当前时间 minutesToAdd 分钟之后的时间
     *
     * @param minutesToAdd 时间
     * @return 时间戳
     */
    public static Long plusMinute(int minutesToAdd) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.withNano(0).plusMinutes(minutesToAdd).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当天的00:00:00 000
     *
     * @param date 时间
     * @return 时间
     */
    public static Date startOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当天的3:59:59 999
     *
     * @param date 时间
     * @return 时间
     */
    public static Date endOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 根据时间戳返回指定时区，指定格式的字符串
     *
     * @param time     时间戳
     * @param timeZone +08:00
     * @param pattern  示例：yyyy-MM-dd HH:mm:ss
     * @return 示例：2021-09-28 16:31:13UTC+08:00
     */
    public static String formatterToUTCTimeZone(Long time, String timeZone, String pattern) {
        if (null == time || 0 == time) {
            return null;
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        ZoneId zoneId = TimeZone.getTimeZone("GMT" + timeZone).toZoneId();
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId)) + "UTC" + timeZone;
    }

    /**
     * 字符串转Date
     *
     * @param dateStr 时间字符串，例如：2022-09-08
     */
    public static Date parseDate(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 时间字符串
     */
    public static LocalDateTime parseDateTime(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return parseDateTime(dateStr, formatter);
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 时间字符串
     */
    public static LocalDateTime parseDateTime(String dateStr, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * 转换成Java8时间
     */
    public static LocalDateTime fromDate(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 转换成Java8时间
     *
     * @param milliseconds 毫秒数
     * @return LocalDateTime
     */
    public static LocalDateTime fromMilliseconds(final long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

}
