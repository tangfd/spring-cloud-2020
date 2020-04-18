package com.tfd.cloud.study.provider.exception;

import com.tfd.cloud.study.common.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @since TangFD 2020-04-18
 **/
@Slf4j
@RestControllerAdvice
public class ProviderExceptionHandler {
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception ex) {
        log.error("请求执行异常！", ex);
        return new JsonResult(false, 500, ex.getMessage());
    }
}
