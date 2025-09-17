package com.tao.utils;

import lombok.Getter;

/**
 * API响应状态枚举
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/16 22:32
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(400, "操作失败");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
