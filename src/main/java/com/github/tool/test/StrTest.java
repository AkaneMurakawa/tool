package com.github.tool.test;

import static java.lang.StringTemplate.STR;

public class StrTest {

    public static void main(String[] args) {
        int x = 10;
        int y = 20;
        String result = STR."\{x} + \{y} = \{x + y}";
        System.out.println(result);
    }
}
