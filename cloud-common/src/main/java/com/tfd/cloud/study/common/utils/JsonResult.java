package com.tfd.cloud.study.common.utils;

import lombok.Data;

/**
 * @since TangFD 2020-04-18
 **/
@Data
public class JsonResult<T> {
    private Integer code;
    private Boolean success = true;
    private String message;
    private T data;

    public JsonResult() {
    }

    public JsonResult(Integer code) {
        this(true, code, null, null);
    }

    public JsonResult(String message) {
        this(true, null, message, null);
    }

    public JsonResult(boolean success, String message) {
        this(success, null, message, null);
    }

    public JsonResult(boolean success, Integer code) {
        this(success, code, null, null);
    }

    public JsonResult(boolean success, Integer code, String message) {
        this(success, code, message, null);
    }

    public JsonResult(boolean success, String message, T data) {
        this(success, null, message, data);
    }

    public JsonResult(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
