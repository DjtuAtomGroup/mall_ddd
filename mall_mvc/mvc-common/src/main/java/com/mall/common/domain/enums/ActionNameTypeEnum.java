package com.mall.common.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionNameTypeEnum {

    QR_SCENE("QR_SCENE", "临时的整型参数值"),
    QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
    QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
    QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

    private final String code;

    private final String info;

}
