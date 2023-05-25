package com.github.tool.core.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 */
public class JDKProxyFactory<T> {

    public T newInstance(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JDKProxy(target));
    }
}
