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
        LogAspect aspect = new LogAspect();
        // JDK代理
        UserServiceImpl userService = new UserServiceImpl();
        JDKProxyFactory jdkProxyFactory = new JDKProxyFactory();
        UserService proxy = jdkProxyFactory.newInstance(userService, aspect);
        proxy.hello();

        System.out.println();

        // Cglib代理
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory();
        proxy = cglibProxyFactory.newInstance(userService, aspect);
        proxy.hello();
    }
}
