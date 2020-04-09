package com.doinb.spring.service;

import org.springframework.stereotype.Service;

// jdk动态代理时开启
//@Service("userService")
//public class UserServiceImpl {

// Cglib动态代理时开启
@Service
public class UserServiceImpl implements UserService {

    public Object login(String name) {
        return String.format("登录成功！当前登录用户名：%s", name);
    }

}
