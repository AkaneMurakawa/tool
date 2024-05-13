package com.github.tool.test;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

public class NumberEvalTest {

    public static void main(String[] args) {
        double calculate = NumberUtil.calculate("50-50");
        System.out.println(calculate);
        System.out.println(new BigDecimal(calculate).toPlainString());
    }
}
