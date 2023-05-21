package com.github.tool.io;

import cn.hutool.core.io.FileUtil;
import com.github.tool.sftp.prop.SftpProperties;
import com.github.tool.sftp.util.SftpUtil;
import com.github.tool.spring.SpringUtil;
import com.jcraft.jsch.SftpException;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FileUploadUtil
 */
public class FileUploadUtil {

    /**
     * test
     */
    public static void main(String[] args) throws IOException, SftpException {
        String customerCode = "10001";
        // bill-10001-2023-01-02.csv
        String filename = String.join("-",
                "bill",
                customerCode,
                FileUtils.getDateDir("yyyyMMdd") + ".csv");
        File tmpFile = new File(FileUtil.getTmpDir(), filename);
        tmpFile.createNewFile();
        // bill/10001/2023/01/02
        String sftpFilePath = String.join("/",
                "bill",
                customerCode,
                FileUtils.getDatePath());

        FileUploadUtil.upload2Sftp(tmpFile.getAbsoluteFile(), sftpFilePath);
    }

    /**
     * 上传本地文件到sftp
     *
     * @param file         本地文件
     * @param sftpFilePath sftp文件路径，以/作为目录分隔符，前后不加分隔符，例如：sftp/bill
     */
    public static void upload2Sftp(File file, String sftpFilePath) throws IOException, SftpException {
        // 配置sftp
        SftpProperties properties = SpringUtil.getApplicationContext().getBean(SftpProperties.class);
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
