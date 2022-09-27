package icu.sunnyc.navicat.example.controller;

import cn.hutool.core.util.IdUtil;
import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hc
 * @date Created in 2022/9/27 20:30
 * @modified
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUniqueId")
    @ApiOperation("获取当前客户端唯一标识")
    public CommonResult getUniqueId() {
        return CommonResult.success(IdUtil.objectId());
    }

}
