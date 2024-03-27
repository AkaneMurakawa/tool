package com.github.tool.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * 虚拟线程
 *
 * @since 21
 */
public class VirtualThreadTest {

    public static void main(String[] args) {
        // example1();
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> consumer(System.out::println, "do task"));
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 限流并发的信号量，permits: 10
    private static final Semaphore POOL = new Semaphore(10);

    static <T> void consumer(Consumer consumer, T t) {
        try {
            System.out.println("acquire: " + POOL.availablePermits());
            POOL.acquire();
            System.out.println("acquire success");
        } catch (InterruptedException e) {
            System.out.println("permits 10");
        }

        try {
            consumer.accept(t);
        } finally {
            POOL.release();
        }
    }

    static void example1() {
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
