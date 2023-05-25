package com.github.tool.core.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * Cglib代理工厂
 */
public class CglibProxyFactory<T> {

    private final Enhancer enhancer = new Enhancer();

    public T newInstance(T target) {
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxy(target));
        return (T) enhancer.create();
    }
}
