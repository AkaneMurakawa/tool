package com.github.tool.test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextTest {

    public static void main(String[] args) {
        System.out.println(new BigDecimal("246.8", new MathContext(1, RoundingMode.CEILING))); // 3E+2

        BigDecimal d = new BigDecimal("50.27");
        BigDecimal r = new BigDecimal("0.5");
        System.out.println(d.multiply(r).setScale(3, RoundingMode.HALF_UP)); // 25.135
        System.out.println(d.multiply(r).setScale(3)); // 25.135

        // 16.756666666666668
        System.out.println(d.divide(new BigDecimal("3"), 3, RoundingMode.HALF_UP)); // 16.757
        System.out.println(d.divide(new BigDecimal("3"), new MathContext(3))); // 16.8
        System.out.println(d.divide(new BigDecimal("3"), new MathContext(2))); // 17
        System.out.println(d.divide(new BigDecimal("3"), new MathContext(1)).toPlainString()); // 20
    }
}
