package tech.xixing.qaqmall.enums;

import lombok.Getter;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 15:26
 */
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1,"在线支付");
    private Integer code;
    private String msg;

    PaymentTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
