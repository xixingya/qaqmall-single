package tech.xixing.qaqmall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/4 8:30
 */
@Data
public class UserLoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
