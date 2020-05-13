package tech.xixing.qaqmall.enums;

import lombok.Getter;

/**角色0-管理员 1-普通用户
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 9:18
 */
@Getter
public enum  RoleEnum {
    ADMIN(0,"管理员"),
    CUSTOMER(1,"顾客");
    Integer code;
    String msg;
    RoleEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
