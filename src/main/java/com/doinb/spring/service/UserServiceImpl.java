package com.doinb.spring.service;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl {

//@Service
//public class UserServiceImpl implements UserService {

    public Object login(String name) {
        return String.format("登录成功！当前登录用户名：%s", name);
    }

}
