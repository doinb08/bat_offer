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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/bat")
@Api(value = "登录", tags = "登录")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    IpRegionUtils ipRegionUtils;

    @Autowired
    @Qualifier("asyncExecutor")
    AsyncTaskExecutor asyncTaskExecutor;

    @ApiOperation("登录")
    @GetMapping(value = "/user/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "用户名", paramType = "query"),
    })
    public Object login(@RequestParam String username) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("5");
        list.add("2");
        list.add("7");
        list.add("6");
        for (String s : list) {
            asyncTaskExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程名字：" + Thread.currentThread().getName() + "序号：" + s);
            });
        }
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
        return BaseResponse.success("region:" + region + " region2:" + region2);
    }
}
