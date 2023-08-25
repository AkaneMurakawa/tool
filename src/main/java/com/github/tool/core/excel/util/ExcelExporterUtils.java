package com.github.tool.core.excel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.tool.core.excel.page.BasePage;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * 导出工具类
 */
public class ExcelExporterUtils {

    private ExcelExporterUtils() {

    }

    /**
     * Web全部导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response 响应
     * @param fileName 文件名
     * @param data     数据
     * @param <T>      类型
     */
    @SneakyThrows
    public static <T> void export(HttpServletResponse response, String fileName, List<T> data) {
        export(response, fileName, "sheet", data);
    }

    /**
     * Web全部导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response  响应
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param data      数据
     * @param <T>       类型
     */
    @SneakyThrows
    public static <T> void export(HttpServletResponse response, String fileName, String sheetName, List<T> data) {
        export(response, fileName, sheetName, data, null);
    }

    /**
     * Web全部导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response  响应
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param data      数据
     * @param handler   自定义处理器
     * @param <T>       类型
     */
    @SneakyThrows
    public static <T> void export(HttpServletResponse response,
                                  String fileName,
                                  String sheetName,
                                  List<T> data,
                                  WriteHandler handler) {
        ExcelWriter excelWriter = null;
        try {
            Assert.notEmpty(data, "无数据");
            preprocessingHttpServletResponse(response, fileName);
            excelWriter = EasyExcelFactory.write(response.getOutputStream(), data.get(0).getClass()).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).registerWriteHandler(handler).build();
            excelWriter.write(data, writeSheet);
        } catch (Exception e) {
            processingWriteException(response, e);
        } finally {
            Optional.ofNullable(excelWriter).ifPresent(ExcelWriter::finish);
        }
    }

    /**
     * Web分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response     响应
     * @param fileName     文件名
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(HttpServletResponse response,
                                                      String fileName,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction) {
        export(response, fileName, "sheet", clazz, page, pageFunction);
    }

    /**
     * Web分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response     响应
     * @param fileName     文件名
     * @param sheetName    sheet名
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(HttpServletResponse response,
                                                      String fileName,
                                                      String sheetName,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction) {
        export(response, fileName, sheetName, clazz, page, pageFunction, null);
    }

    /**
     * Web分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param response     响应
     * @param fileName     文件名
     * @param sheetName    sheet名
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param handler      自定义处理器
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(HttpServletResponse response,
                                                      String fileName,
                                                      String sheetName,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction,
                                                      WriteHandler handler) {
        ExcelWriter excelWriter = null;
        try {
            preprocessingHttpServletResponse(response, fileName);
            excelWriter = EasyExcelFactory.write(response.getOutputStream(), clazz).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).registerWriteHandler(handler).build();
            // 分页写入
            pageWriteSheet(excelWriter, writeSheet, page, pageFunction);
        } catch (Exception e) {
            processingWriteException(response, e);
        } finally {
            Optional.ofNullable(excelWriter).ifPresent(ExcelWriter::finish);
        }
    }

    /**
     * 分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param outputStream 流
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(OutputStream outputStream,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction) {
        export(outputStream, "sheet", clazz, page, pageFunction);
    }

    /**
     * 分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param outputStream 流
     * @param sheetName    sheet名
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(OutputStream outputStream,
                                                      String sheetName,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction) {
        export(outputStream, sheetName, clazz, page, pageFunction, null);
    }

    /**
     * 分页导出
     * <a here="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson">文档</a>
     *
     * @param outputStream 流
     * @param sheetName    sheet名
     * @param clazz        class类
     * @param page         查询条件
     * @param pageFunction 分页查询
     * @param handler      自定义处理器
     * @param <T>          类型
     * @param <P>          分页类型
     */
    @SneakyThrows
    public static <T, P extends BasePage> void export(OutputStream outputStream,
                                                      String sheetName,
                                                      Class<T> clazz,
                                                      P page,
                                                      Function<P, IPage<T>> pageFunction,
                                                      WriteHandler handler) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcelFactory.write(outputStream, clazz).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).registerWriteHandler(handler).build();
            // 分页写入
            pageWriteSheet(excelWriter, writeSheet, page, pageFunction);
        } finally {
            Optional.ofNullable(excelWriter).ifPresent(ExcelWriter::finish);
        }
    }

    /**
     * HttpServletResponse前置处理
     */
    @SneakyThrows
    public static void preprocessingHttpServletResponse(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // URLEncoder.encode可以防止中文乱码
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    /**
     * HttpServletResponse异常处理
     */
    @SneakyThrows
    public static void processingWriteException(HttpServletResponse response, Exception e) {
        // 重置response
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Map<String, String> map = new HashMap<>();
        map.put("status", "failure");
        map.put("message", "下载文件失败" + e.getMessage());
        response.getWriter().println(JSON.toJSONString(map));
    }

    /**
     * 全部填充
     * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/fill">文档</a>
     *
     * @param fileName         文件名
     * @param templateFileName 模板文件名
     * @param data             数据
     * @param <T>              类型
     */
    public static <T> void fill(String fileName, String templateFileName, Collection<T> data) {
        fill(fileName, templateFileName, data, null);
    }

    /**
     * 全部填充
     * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/fill">文档</a>
     *
     * @param fileName         文件名
     * @param templateFileName 模板文件名
     * @param data             数据
     * @param handler          自定义处理器
     * @param <T>              类型
     */
    public static <T> void fill(String fileName, String templateFileName, Collection<T> data, WriteHandler handler) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcelFactory.write(fileName).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet().registerWriteHandler(handler).build();
            excelWriter.fill(data, writeSheet);
        } finally {
            Optional.ofNullable(excelWriter).ifPresent(ExcelWriter::finish);
        }
    }

    /**
     * 分页填充
     * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/fill">文档</a>
     *
     * @param fileName         文件名
     * @param templateFileName 模板文件名
     * @param page             查询条件
     * @param pageFunction     分页查询
     * @param <T>              类型
     * @param <P>              分页类型
     */
    public static <T, P extends BasePage> void fill(String fileName,
                                                    String templateFileName,
                                                    P page,
                                                    Function<P, IPage<T>> pageFunction) {
        fill(fileName, templateFileName, page, pageFunction, null);
    }

    /**
     * 分页填充
     * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/fill">文档</a>
     *
     * @param fileName         文件名
     * @param templateFileName 模板文件名
     * @param pageFunction     分页查询
     * @param page             查询条件
     * @param <T>              类型
     * @param <P>              分页类型
     */
    public static <T, P extends BasePage> void fill(String fileName,
                                                    String templateFileName,
                                                    P page,
                                                    Function<P, IPage<T>> pageFunction,
                                                    WriteHandler handler) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcelFactory.write(fileName).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet().registerWriteHandler(handler).build();
            // 分页写入
            pageWriteSheet(excelWriter, writeSheet, page, pageFunction);
        } finally {
            Optional.ofNullable(excelWriter).ifPresent(ExcelWriter::finish);
        }
    }

    /**
     * 分页写入
     *
     * @param page         查询条件
     * @param pageFunction 分页查询
     */
    private static <T, P extends BasePage> void pageWriteSheet(ExcelWriter excelWriter,
                                                               WriteSheet writeSheet,
                                                               P page,
                                                               Function<P, IPage<T>> pageFunction) {
        boolean hasNext;
        do {
            IPage<T> apply = pageFunction.apply(page);
            List<?> data = apply.getRecords();
            if (CollectionUtils.isEmpty(data)) {
                return;
            }
            // 写入
            excelWriter.write(data, writeSheet);
            hasNext = apply.getCurrent() * apply.getSize() < apply.getTotal();
            page.setCurrent(page.getCurrent() + 1);
        } while (hasNext);
    }
}
