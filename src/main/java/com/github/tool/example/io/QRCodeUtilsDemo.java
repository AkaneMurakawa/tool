package com.github.tool.example.io;

import cn.hutool.core.io.FileUtil;
import com.github.tool.core.io.QRCodeUtils;
import com.github.tool.core.io.enums.LabelTypeEnum;

import java.io.File;
import java.io.IOException;

public class QRCodeUtilsDemo {

    /**
     * test
     */
    public static void main(String[] args) throws IOException {
        String skuCode = "SKU-001";
        String filename = skuCode + ".png";
        File tmpFile = new File(FileUtil.getTmpDir(), filename);
        tmpFile.createNewFile();
        // 生成70*40一维码
        QRCodeUtils.createBarcodeWriteFile(skuCode, LabelTypeEnum.LABEL_7_4, tmpFile);

        skuCode = "SKU-002";
        filename = skuCode + ".png";
        tmpFile = new File(FileUtil.getTmpDir(), filename);
        tmpFile.createNewFile();
        // 生成二维码
        QRCodeUtils.createQRCodeWriteFile(skuCode, 128, tmpFile);
    }
}
