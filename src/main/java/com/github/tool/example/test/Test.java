package com.github.tool.example.test;

import java.io.IOException;

import static java.lang.StringTemplate.STR;

public class Test {

    public static void main(String[] args) throws IOException {
        int x = 10;
        int y = 20;
        String result = STR. "\{x} + \{y} = \{x + y}";
        System.out.println(result);
        // virtualThread();
    }

    // 选择合适的分批大小
    // static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
    //
    // static void vectorComputation(float[] a, float[] b, float[] c) {
    //     int i = 0;
    //     int upperBound = SPECIES.loopBound(a.length);
    //     for (; i < upperBound; i += SPECIES.length()) {
    //         FloatVector va = FloatVector.fromArray(SPECIES, a, i);
    //         FloatVector vb = FloatVector.fromArray(SPECIES, b, i);
    //         var vc = va.mul(va).add(vb.mul(vb)).neg();
    //         vc.intoArray(c, i);
    //     }
    // }

    /**
     * 虚拟线程
     *
     * @since 19
     */
    public static void virtualThread() {
        // long start = System.currentTimeMillis();
        // ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        // IntStream.range(0, 100_000).forEach(i ->
        //         executor.submit(() -> {
        //             Thread.sleep(1000);
        //             return i;
        //         })
        // );
        //
        // // 提交了 10 万个虚拟线程，每个线程休眠 1 秒钟，1秒左右完成
        // System.out.println(String.format("耗时: %s ms", (System.currentTimeMillis() - start)));
    }
}