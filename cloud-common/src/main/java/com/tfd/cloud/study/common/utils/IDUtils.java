package com.tfd.cloud.study.common.utils;

import java.util.UUID;

/**
 * @since TangFD 2020-04-18
 **/
public class IDUtils {

    public static String getUUId32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
