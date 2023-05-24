package com.github.tool.core.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 * PdfBoxUtil
 */
public class PdfBoxUtil {

    /**
     * 合并多个pdf文件
     *
     * @param files      文件列表
     * @param targetPath 合并路径
     */
    public static File mergeMulFile(List<File> files, String targetPath) throws IOException {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        for (File f : files) {
            if (f.exists() && f.isFile()) {
                mergePdf.addSource(f);
            }
        }
        // 设置合并生成pdf文件名称
        mergePdf.setDestinationFileName(targetPath);
        // 合并pdf
        mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        return new File(targetPath);
    }
}