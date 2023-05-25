package com.github.tool.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK代理
 */
public class JDKProxy implements InvocationHandler {

    private final Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
