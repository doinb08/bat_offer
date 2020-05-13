package com.doinb.web.controller;

import com.doinb.web.vo.BaseResponse;
import com.doinb.web.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * 用于演示 swagger 注解使用
 */
@RestController
@RequestMapping(value = "/bat")
@Api(value = "Swagger注解演示", tags = "Swagger注解演示")
public class SwaggerController {

    /**
     * #@ApiIgnore 注解： 隐藏不需要暴露的接口，比如开发调试用的清除信息接口等，高危操作
     *
     * @return BaseResponse
     */
    @ApiIgnore
    @DeleteMapping(value = "/delete_user")
    public BaseResponse<?> deleteUser(@RequestParam int userId) {
        return BaseResponse.success("删除用户成功，用户ID：" + userId);
    }

    /**
     * 注解形式 1
     * @param userVo 通过对象传参
     * @return resp
     */
    @ApiOperation(value = "添加用户", consumes = "application/json")
    @PostMapping(value = "/add_user")
    public BaseResponse<?> addUser(@Valid @RequestBody UserVo userVo) {

        return BaseResponse.success("添加用户成功，用户名：" + userVo.getUsername());
    }

    /**
     * 注解形式 2
     * @param map 通过对象传参
     * @return resp
     */
    @PostMapping(value = "/add_user2")
    @ApiOperation(value = "添加用户", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "age", required = true, value = "年龄", paramType = "body"),
            @ApiImplicitParam(name = "username", required = true, value = "名字", paramType = "body"),
            @ApiImplicitParam(name = "password", required = true, value = "密码", paramType = "body")
    })
    public BaseResponse<?> addUser2(@Valid @RequestBody Map<String, String> map) {
        return BaseResponse.success("添加用户成功，用户名：" + map.get("username"));
    }

}
