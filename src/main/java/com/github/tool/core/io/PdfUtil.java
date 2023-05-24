package com.github.tool.core.io;

import com.google.common.collect.Lists;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.*;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * PdfUtil
 */
public class PdfUtil {

    /**
     * base64转pdf
     */
    public static void createPdfFromBase64(String base64, File file) throws IOException, DocumentException {
        createPdfFromBase64(Lists.newArrayList(base64), file);
    }

    /**
     * 多个base64转pdf
     */
    public static void createPdfFromBase64(List<String> base64List, File file) throws IOException, DocumentException {
        @Cleanup FileOutputStream outputStream = new FileOutputStream(file);
        Document document = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64List.get(0));
            PdfReader pdfReader = new PdfReader(bytes);
            document = new Document(pdfReader.getPageSize(1));
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            for (String base64 : base64List) {
                bytes = Base64.getDecoder().decode(base64);
                pdfReader = new PdfReader(bytes);
                int pages = pdfReader.getNumberOfPages();
                // 遍历pdf文档
                for (int i = 1; i <= pages; i++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(pdfReader, i);
                    copy.addPage(page);
                }
            }
        } finally {
            Optional.ofNullable(document).ifPresent(Document::close);
        }
    }

    /**
     * 合并多个pdf文件
     *
     * @param files  文件列表
     * @param target 保存文件
     */
    public static void mergeMulFile(List<File> files, File target) throws IOException, DocumentException {
        @Cleanup FileOutputStream outputStream = new FileOutputStream(target);
        Document document = null;
        try {
            @Cleanup FileInputStream inputStream = new FileInputStream(files.get(0));
            PdfReader pdfReader = new PdfReader(inputStream);
            document = new Document(pdfReader.getPageSize(1));
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            for (File file : files) {
                inputStream = new FileInputStream(file);
                pdfReader = new PdfReader(inputStream);
                int pages = pdfReader.getNumberOfPages();
                // 遍历pdf文档
                for (int i = 1; i <= pages; i++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(pdfReader, i);
                    copy.addPage(page);
                }
            }
        } finally {
            Optional.ofNullable(document).ifPresent(Document::close);
        }
    }
}
