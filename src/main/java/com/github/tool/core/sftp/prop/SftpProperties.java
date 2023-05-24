package com.github.tool.core.sftp.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SftpProperties
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "my.sftp")
public class SftpProperties {

    /**
     * Host
     */
    private String host;
    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接自动切换目录, /结尾，例如：/usr/local/data/sftp
     */
    private String basePath = "/";
}

