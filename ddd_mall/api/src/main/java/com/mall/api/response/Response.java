package com.mall.api.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {

    private Integer code;

    private String message;

    private T data;
}
