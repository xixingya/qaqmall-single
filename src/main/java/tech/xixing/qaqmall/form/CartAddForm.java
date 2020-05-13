package tech.xixing.qaqmall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 8:53
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected=true;

}
