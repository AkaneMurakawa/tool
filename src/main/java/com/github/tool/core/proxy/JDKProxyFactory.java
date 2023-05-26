package com.github.tool.core.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 */
public class JDKProxyFactory {

    public <T> T newInstance(T target, Aspect aspect) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JDKProxy(target, aspect));
    }
}
