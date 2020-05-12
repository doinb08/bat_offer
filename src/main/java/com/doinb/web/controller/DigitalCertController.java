package com.doinb.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.doinb.web.vo.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mr_do
 */
@RestController
@RequestMapping("/bat")
public class DigitalCertController {

    @ApiOperation("查询数字证书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serialNo", required = true, value = "序列号", paramType = "query"),
            @ApiImplicitParam(name = "idCard", required = true, value = "身份证号", paramType = "query"),
    })
    @GetMapping("/fetch_cert")
    public BaseResponse<?> getCertificateDetail(@RequestParam(required = false) String serialNo,
                                                @RequestParam(required = false) String idCard) {
        if (StringUtils.isBlank(serialNo) || StringUtils.isBlank(idCard)) {
            return BaseResponse.badRequest("参数不正确，请核实");
        }
        JSONObject object = new JSONObject();
        object.put("serialNo", serialNo);
        object.put("idCard", idCard);
        return BaseResponse.success(object);
    }
}
