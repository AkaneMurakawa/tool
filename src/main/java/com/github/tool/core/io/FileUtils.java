package com.github.tool.core.io;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * FileUtils
 */
public class FileUtils {

    /**
     * 获取日期文件夹
     *
     * @param format 文件夹格式，例如："yyyyMMdd"，"yyyy-MM-dd"
     * @return 日期文件夹
     */
    public static String getDateDir(String format) {
        return LocalDateTimeUtil.format(LocalDate.now(), format);
    }

    /**
     * 获取日期目录
     *
     * @return 日期目录，例如：2022/09/08
     */
    public static String getDatePath() {
        return LocalDateTimeUtil.format(LocalDate.now(), "yyyy/MM/dd");
    }

    /**
     * 获取年
     */
    public static int getYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取月
     */
    public static int getMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取日
     */
    public static int getDay() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 读取文本
     *
     * @param filename 文件名
     */
    public static void readLine(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            // process the line
            System.out.println(line);
        }
        br.close();
    }
}
