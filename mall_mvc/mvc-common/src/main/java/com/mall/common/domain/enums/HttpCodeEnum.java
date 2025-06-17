package com.mall.common.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    NOT_FOUND(404, "未找到该资源"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_SUPPORTED(415, "不支持的媒体类型"),
    NOT_ACCEPTABLE(406, "不接受"),
    REQUEST_TIMEOUT(408, "请求超时"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    CONFLICT(409, "冲突");

    private final Integer code;

    private final String message;
}
