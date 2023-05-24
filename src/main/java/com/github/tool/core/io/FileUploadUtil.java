package com.github.tool.core.io;

import com.github.tool.core.sftp.prop.SftpProperties;
import com.github.tool.core.sftp.util.SftpUtil;
import com.github.tool.core.spring.SpringUtil;
import com.jcraft.jsch.SftpException;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * FileUploadUtil
 */
@Log4j2
public class FileUploadUtil {

    /**
     * 上传本地文件到sftp
     *
     * @param file         本地文件
     * @param sftpFilePath sftp文件路径，以/作为目录分隔符，前后不加分隔符，例如：sftp/bill
     */
    public static void upload2Sftp(File file, String sftpFilePath) throws IOException, SftpException {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        // 配置sftp
        SftpProperties properties = Optional.ofNullable(applicationContext)
                .map(context -> context.getBean(SftpProperties.class)).orElse(null);
        if (null == properties) {
            log.error("FTP连接未配置");
            return;
        }
        SftpUtil sftpUtil = new SftpUtil(properties);
        try {
            // 文件名
            String name = file.getName();
            String path = properties.getBasePath() + sftpFilePath;
            // 存到sftp文件路径名
            String pathname = String.join("/", path, name);
            // 连接sftp
            sftpUtil.connect();
            sftpUtil.cd(path, "755");
            @Cleanup InputStream inputStream = new FileInputStream(file);
            sftpUtil.touch(inputStream, name);
            sftpUtil.chmod(pathname, "755");
        } finally {
            sftpUtil.disconnect();
        }
    }

}
