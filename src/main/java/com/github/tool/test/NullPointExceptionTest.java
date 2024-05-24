package com.github.tool.test;

/**
 * JDK14空指针提示优化
 */
public class NullPointExceptionTest {

    public static void main(String[] args) {
        Boolean b = null;
        /**
         * Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because "b" is null
         * 	at com.github.tool.test.NullPointExceptionTest.main(NullPointExceptionTest.java:7)
         */
        nullPointExceptionTest("", b);
    }

    public static void nullPointExceptionTest(String a, boolean b) {
    }
}
