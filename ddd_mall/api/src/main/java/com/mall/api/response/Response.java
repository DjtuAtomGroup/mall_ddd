package com.mall.api.response;


import com.mall.api.domain.enums.HttpCodeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(HttpCodeEnum.SUCCESS.getCode())
                .message(HttpCodeEnum.SUCCESS.getMessage())
                .data(data)
                .build();
    }


    public static <T> Response<T> fail(T data) {
        return Response.<T>builder()
                .code(HttpCodeEnum.FAIL.getCode())
                .message(HttpCodeEnum.FAIL.getMessage())
                .data(data)
                .build();
    }


    public static <T> Response<T> unAuthorized(T data) {
        return Response.<T>builder()
                .code(HttpCodeEnum.UNAUTHORIZED.getCode())
                .message(HttpCodeEnum.UNAUTHORIZED.getMessage())
                .data(data)
                .build();
    }
}
