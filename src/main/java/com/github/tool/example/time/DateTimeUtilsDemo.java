package com.github.tool.example.time;

import com.github.tool.core.time.DateTimeUtils;

public class DateTimeUtilsDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println("当前时间 >> " + time);
        System.out.println("获取上一个月的第一天 >> " + DateTimeUtils.beforeMonthFirstDay(time));
        System.out.println("获取上一个月的最后一天 >> " + DateTimeUtils.beforeMonthLastDay(time));

        // 2021-09-20 00:00:00
        //time = 1632067200000L;
        System.out.println("获取上周一 >> " + DateTimeUtils.beforeWeekFirstDay(time));
        System.out.println("获取上周日 >> " + DateTimeUtils.beforeWeekLastDay(time));
        System.out.println(DateTimeUtils.formatterToUTCTimeZone(time, "+08:00", "yyyy-MM-dd HH:mm:ss"));
    }
}
