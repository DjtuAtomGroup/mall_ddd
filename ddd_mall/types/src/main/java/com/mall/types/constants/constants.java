package com.mall.types.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class constants {
    public final static String SPLIT = ",";

    public final static char[] DIGIT = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ResponseCode {
        SUCCESS("0000", "调用成功"),
        UN_ERROR("0001", "调用失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        NO_LOGIN("0003", "未登录");

        private String code;
        private String info;

    }
}
