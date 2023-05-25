package com.github.tool.example.proxy;

import com.github.tool.core.proxy.CglibProxyFactory;
import com.github.tool.core.proxy.JDKProxyFactory;
import com.github.tool.example.base.UserService;
import com.github.tool.example.base.impl.UserServiceImpl;

public class ProxyDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        // JDK代理
        UserServiceImpl userService = new UserServiceImpl();
        JDKProxyFactory<UserServiceImpl> jdkProxyFactory = new JDKProxyFactory();
        UserService proxy = jdkProxyFactory.newInstance(userService);
        proxy.hello();
        // Cglib代理
        CglibProxyFactory<UserServiceImpl> cglibProxyFactory = new CglibProxyFactory();
        proxy = cglibProxyFactory.newInstance(userService);
        proxy.hello();
    }
}
