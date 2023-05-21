package com.github.tool.base.exception;

import lombok.Getter;

import java.text.MessageFormat;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    private final String message;

    private transient Object[] args;

    public BusinessException(String code) {
        super(code);
        this.code = code;
        this.message = code;
    }

    public BusinessException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 抛出业务异常
     * <p>
     * 国际化翻译会采用{@link MessageFormat#format}进行格式化errMsg
     * 采用如{0},{1}作为占位符的形式
     */
    public BusinessException(String code, String message, Object... args) {
        super(MessageFormat.format(message, args));
        this.code = code;
        this.message = MessageFormat.format(message, args);
        this.args = args;
    }
}
