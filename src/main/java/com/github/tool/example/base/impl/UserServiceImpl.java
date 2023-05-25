package com.github.tool.example.base.impl;

import com.github.tool.example.base.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void hello() {
        System.out.println("hello");
    }
}
