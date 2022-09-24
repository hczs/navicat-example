package icu.sunnyc.navicat.example.exception;

import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/24 11:29
 */
@Slf4j
@RestControllerAdvice
public class ExceptionConfigController {

    @ExceptionHandler
    public CommonResult exceptionHandler(Exception e){
        log.error("拦截到服务器异常信息：{}", e.getMessage(), e);
        return CommonResult.failed("服务器异常，请稍后再试");
    }

}
