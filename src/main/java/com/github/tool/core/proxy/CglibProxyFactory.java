package com.github.tool.core.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * Cglib代理工厂
 */
public class CglibProxyFactory {

    public <T> T newInstance(T target, Aspect aspect) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxy(target, aspect));
        return (T) enhancer.create();
    }
}
