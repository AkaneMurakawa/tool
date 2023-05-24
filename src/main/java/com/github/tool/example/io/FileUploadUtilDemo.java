package com.github.tool.example.io;

import cn.hutool.core.io.FileUtil;
import com.github.tool.core.io.FileUploadUtil;
import com.github.tool.core.io.FileUtils;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;

public class FileUploadUtilDemo {

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
}
