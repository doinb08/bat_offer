package com.doinb.web.controller;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.doinb.spring.aop.SpringContextHolder;
import com.doinb.spring.service.UserService;
import com.doinb.spring.service.UserServiceImpl;
import com.doinb.web.vo.BaseResponse;
import com.doinb.web.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 用于演示 swagger 注解使用
 */
@Slf4j
@RestController
@RequestMapping(value = "/bat")
@Api(value = "Swagger注解演示", tags = "Swagger注解演示")
public class SwaggerController {

    /**
     * key=id, value=UserVo
     */
    private static final LRUCache<Long, UserVo> lruCache = CacheUtil.newLRUCache(1000);

    /**
     * 雪花算法
     */
    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * 获取容器中的值
     */
    @Autowired
    Map<String, UserService> beanMap = MapUtil.newHashMap();

    @Autowired
    UserService userService;

    /**
     * 查询用户demo
     *
     * @return BaseResponse
     */
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/listUser")
    public BaseResponse<?> list() {
        List<UserVo> list = CollUtil.newArrayList();
        for (UserVo userVo : lruCache) {
            list.add(userVo);
        }

        // 获取bean的三种方式
//        UserService userService1 = SpringContextHolder.getBean(UserService.class);
//        log.info("userService1={}", userService1);
//
//        UserServiceImpl userService2 = (UserServiceImpl) SpringUtil.getBean(UserService.class);
//        log.info("userService2={}", userService2);


        UserServiceImpl userService3 = (UserServiceImpl) beanMap.get("userServiceImpl");
        log.info("userService3={}", userService3);

        userService3.login("张三");

        return BaseResponse.success(list);
    }

    /**
     * 注解形式
     *
     * @param userVo 通过对象传参
     * @return resp
     */
    @ApiOperation(value = "添加用户", consumes = "application/json")
    @PostMapping(value = "/add_user")
    public BaseResponse<?> addUser(@Valid @RequestBody UserVo userVo) {
        long userId = snowflake.nextId();
        userVo.setUserId(userId);
        lruCache.put(userId, userVo);
        return BaseResponse.success("添加用户成功，用户名：" + userVo.getUsername());
    }

    /**
     * #@ApiIgnore 注解： 隐藏不需要暴露的接口，比如开发调试用的清除信息接口等，高危操作
     *
     * @return BaseResponse
     */
    // @ApiIgnore
    @DeleteMapping(value = "/delete_user")
    public BaseResponse<?> deleteUser(@RequestParam long userId) {
        lruCache.remove(userId);
        return BaseResponse.success("删除用户成功，用户ID：" + userId);
    }

}
