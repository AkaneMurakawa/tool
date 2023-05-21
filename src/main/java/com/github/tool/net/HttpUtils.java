package com.github.tool.net;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * HttpUtils
 */
@Log4j2
public class HttpUtils {

    private HttpUtils() {
    }

    private static final String MEDIA_APPLICATION = "application";
    private static final String MEDIA_APPLICATION_JSON = "json";
    private static final String MEDIA_APPLICATION_FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String MEDIA_APPLICATION_XML = "xml";
    private static final String MEDIA_APPLICATION_ATOM_XML = "atom+xml";
    private static final String MEDIA_APPLICATION_RSS_XML = "rss+xml";
    private static final String MEDIA_APPLICATION_TEXT = "text";
    private static final String MEDIA_FORM_DATA = "multipart";

    /**
     * 获取请求体内容
     */
    public static Charset getCharsetRecord(HttpHeaders headers) {
        if (headers.getContentLength() > 0) {
            MediaType contentType = headers.getContentType();
            if (contentType == null) {
                log.debug("无法获取请求体");
                return null;
            } else if (!isSupportRecordType(contentType)) {
                log.debug("不支持记录的请求类型");
                return null;
            } else {
                return getMediaTypeCharset(contentType);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取请求类型
     */
    public static Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (mediaType != null && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    public static boolean isSupportRecordType(MediaType contentType) {
        String type = contentType.getType();
        String subType = contentType.getSubtype();
        switch (type) {
            case MEDIA_APPLICATION:
                return MEDIA_APPLICATION_JSON.equals(subType)
                        || MEDIA_APPLICATION_FORM_URLENCODED.equals(subType)
                        || MEDIA_APPLICATION_XML.equals(subType)
                        || MEDIA_APPLICATION_ATOM_XML.equals(subType)
                        || MEDIA_APPLICATION_RSS_XML.equals(subType);
            case MEDIA_APPLICATION_TEXT:
            case MEDIA_FORM_DATA:
                return true;
        }
        return false;
    }

    /**
     * 获取客户端IP
     */
    public static String getClientIp(@NonNull ServerHttpRequest request) {
        String ip = null;
        HttpHeaders headers = request.getHeaders();
        try {
            ip = headers.getFirst("X-Real-IP");
            if (unknownIP(ip)) {
                ip = headers.getFirst("x-forwarded-for");
            }
            if (unknownIP(ip)) {
                ip = headers.getFirst("Proxy-Client-IP");
            }
            if (unknownIP(ip)) {
                ip = headers.getFirst("X-Proxy-Client-IP");
            }
            if (unknownIP(ip)) {
                ip = headers.getFirst("HTTP_CLIENT_IP");
            }
            if (unknownIP(ip)) {
                ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
            }
            if (unknownIP(ip)) {
                ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }

        //使用代理，则获取第一个IP地址
        if (StringUtils.isNotEmpty(ip) && ip.length() > 15) {
            if (ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    private static boolean unknownIP(String ip) {
        return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }

}
