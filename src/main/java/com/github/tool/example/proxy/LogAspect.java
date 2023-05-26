package com.github.tool.example.proxy;

import com.github.tool.core.proxy.Aspect;

import java.lang.reflect.Method;

public class LogAspect implements Aspect {
    @Override
    public boolean before(Object obj, Method method, Object[] args) {
        System.out.println("before");
        return true;
    }

    @Override
    public boolean after(Object target, Method method, Object[] args, Object returnVal) {
        System.out.println("after");
        return true;
    }

    @Override
    public boolean afterException(Object target, Method method, Object[] args, Throwable e) {
        System.out.println("afterException");
        return true;
    }
}
