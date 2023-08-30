package com.github.tool.example.io;

import com.github.tool.core.io.FileUtils;

import java.io.IOException;

public class FileUtilsDemo {

    /**
     * test
     */
    public static void main(String[] args) throws IOException {
        FileUtils.readString("E:\\env.json");
    }
}
