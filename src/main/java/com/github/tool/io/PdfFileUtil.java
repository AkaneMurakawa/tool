package com.github.tool.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 * PdfFileUtil
 */
public class PdfFileUtil {

    /**
     * test
     */
    public static void main(String[] args) throws IOException {
        List<File> files = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            files.add(new File("D:\\tmp\\" + i + ".pdf"));
        }
        File f = mergeMulFile(files, "D:\\merge.pdf");
        System.out.println(f.length());
    }

    /**
     * 合并pdf
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