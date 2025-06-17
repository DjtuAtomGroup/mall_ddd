package com.mall.common.domain.vo;


import com.mall.common.domain.enums.HttpCodeEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(HttpCodeEnum.SUCCESS.getCode())
                .message(HttpCodeEnum.SUCCESS.getMessage())
                .data(data)
                .build();
    }


    public static <T> Result<T> failed(T data) {
        return Result.<T>builder()
               .code(HttpCodeEnum.FAIL.getCode())
               .message(HttpCodeEnum.FAIL.getMessage())
                .data(data)
               .build();
    };


    public static <T> Result<T> internalError(T data) {
        return Result.<T>builder()
              .code(HttpCodeEnum.INTERNAL_SERVER_ERROR.getCode())
              .message(HttpCodeEnum.INTERNAL_SERVER_ERROR.getMessage())
               .data(data)
              .build();
    }


    public static <T> Result<T> unauthorized(T data) {
        return Result.<T>builder()
             .code(HttpCodeEnum.UNAUTHORIZED.getCode())
             .message(HttpCodeEnum.UNAUTHORIZED.getMessage())
              .data(data)
             .build();
    }


    public static <T> Result<T> forbidden(T data) {
        return Result.<T>builder()
             .code(HttpCodeEnum.FORBIDDEN.getCode())
             .message(HttpCodeEnum.FORBIDDEN.getMessage())
              .data(data)
             .build();
    }
}
