package com.doinb.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVo {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "名字")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}
