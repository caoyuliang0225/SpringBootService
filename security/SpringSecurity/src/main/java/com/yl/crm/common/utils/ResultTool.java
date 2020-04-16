package com.yl.crm.common.utils;

import com.yl.crm.common.entity.JsonResult;
import com.yl.crm.common.enums.ResultCode;

/**
 * @Author: Hutengfei
 * @Description:
 * @Date Create in 2019/7/22 19:52
 */
public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
}
