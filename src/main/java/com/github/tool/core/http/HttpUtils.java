package com.github.tool.core.http;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * HttpUtils
 */
@Log4j2
public class HttpUtils {

    private HttpUtils() {
    }

    public static String getContentType(HttpHeaders headers) {
        if (headers.getContentLength() > 0) {
            return headers.getFirst(Header.CONTENT_TYPE.getValue());
        }
        return null;
    }

    /**
     * 获取编码，获取失败默认使用UTF-8，获取规则如下：
     *
     * <pre>
     *     1、从Content-Type头中获取编码，类似于：text/html;charset=utf-8
     * </pre>
     *
     * @return 编码，默认UTF-8
     */
    public static Charset getCharset(HttpHeaders headers) {
        final String contentType = getContentType(headers);
        final String charsetStr = HttpUtil.getCharset(contentType);
        return CharsetUtil.parse(charsetStr, CharsetUtil.parse(charsetStr));
    }

    public static String getBody(HttpRequest httpRequest){
        if (Objects.isNull(httpRequest.bodyBytes())) {
            return null;
        }
        return new String(httpRequest.bodyBytes());
    }

    public static String getSize(String str) {
        return getSize(str.length());
    }

    /**
     * 将字节大小转换为KB、MB、GB，并保留两位小数
     */
    public static String getSize(long size) {
        final int GB = 1024 * 1024 * 1024;
        final int MB = 1024 * 1024;
        final int KB = 1024;
        // 格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize;
        if (size / GB >= 1) {
            resultSize = df.format(size / (float) GB) + " GB";
        } else if (size / MB >= 1) {
            resultSize = df.format(size / (float) MB) + " MB";
        } else if (size / KB >= 1) {
            resultSize = df.format(size / (float) KB) + " KB";
        } else {
            resultSize = size + " B";
        }
        return resultSize;
    }

}
