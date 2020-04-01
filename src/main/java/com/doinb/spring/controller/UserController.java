package com.doinb.spring.controller;

import com.doinb.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    /*
    @Autowired
    UserService userService;

    @GetMapping(value = "/user/login")
    public Object login(@RequestParam String username) {
        return userService.login(username);
    }
    */
}
