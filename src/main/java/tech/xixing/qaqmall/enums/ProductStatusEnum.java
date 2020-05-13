package tech.xixing.qaqmall.enums;

import lombok.Getter;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 9:51
 */
@Getter
public enum  ProductStatusEnum {
    ON_SALE(1,"在售"),
    OFF_SALE(2,"下架"),
    DELETE(3,"删除")
    ;


    private Integer code;
    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
