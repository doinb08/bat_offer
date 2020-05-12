package com.doinb.spring.controller;

import com.doinb.spring.service.UserService;
import com.doinb.utils.IpRegionUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bat")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    IpRegionUtils ipRegionUtils;

    @GetMapping(value = "/user/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "用户名", paramType = "query"),
    })
    public Object login(@RequestParam String username) {
        return userService.login(username);
    }

    @GetMapping(value = "/ip_region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", required = true, value = "IP地址", paramType = "query"),
    })
    public Object getIpRegion(@RequestParam String ip) {
        if (!Util.isIpAddress(ip)) {
            log.warn("非法的IP地址");
            return ip;
        }
        return ipRegionUtils.getRegion(ip);
    }
}
