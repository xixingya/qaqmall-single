package tech.xixing.qaqmall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/30 8:53
 */
@Data
public class UserRegisterForm {

    //@NotBlank 用于 String 判断空格
    //@NotEmpty 用于集合
    //@NotNull
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
