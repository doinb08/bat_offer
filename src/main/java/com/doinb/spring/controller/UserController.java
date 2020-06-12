package com.doinb.spring.controller;

import com.doinb.spring.service.UserService;
import com.doinb.utils.IpRegionUtils;
import com.doinb.web.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "登录", tags = "登录")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    IpRegionUtils ipRegionUtils;

    @ApiOperation("登录")
    @GetMapping(value = "/user/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "用户名", paramType = "query"),
    })
    public Object login(@RequestParam String username) {
        return userService.login(username);
    }

    @ApiOperation("解析IP所属区域")
    @GetMapping(value = "/ip_region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", required = true, value = "IP地址", paramType = "query"),
    })
    public BaseResponse<?> getIpRegion(@RequestParam String ip) {
        if (!Util.isIpAddress(ip)) {
            log.warn("非法的IP地址");
            return BaseResponse.badRequest(ip);
        }
        String region = ipRegionUtils.getRegion(ip);
        String region2 = ipRegionUtils.getCityByIP(ip);
        return BaseResponse.success("region:"+region+" region2:"+region2);
    }
}
