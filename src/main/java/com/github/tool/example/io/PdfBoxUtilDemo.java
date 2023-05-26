package com.github.tool.example.io;

import com.github.tool.core.io.PdfBoxUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfBoxUtilDemo {

    /**
     * test
     */
    public static void main(String[] args) throws IOException {
        List<File> files = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            files.add(new File("D:\\tmp\\" + i + ".pdf"));
        }
        // 合并多个pdf文件
        File f = PdfBoxUtil.mergeMulFile(files, "D:\\merge.pdf");
        System.out.println(f.length());
    }

}
