package com.github.tool.example.test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) throws IOException {
        virtualThread();
    }

    /**
     * 虚拟线程
     *
     * @since 19
     */
    public static void virtualThread() {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        IntStream.range(0, 100_000).forEach(i ->
                executor.submit(() -> {
                    Thread.sleep(1000);
                    return i;
                })
        );

        // 提交了 10 万个虚拟线程，每个线程休眠 1 秒钟，1秒左右完成
        System.out.println(String.format("耗时: %s ms", (System.currentTimeMillis() - start)));
    }
}