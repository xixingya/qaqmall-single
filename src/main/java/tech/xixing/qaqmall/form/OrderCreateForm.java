package tech.xixing.qaqmall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/13 9:01
 */
@Data
public class OrderCreateForm {


    @NotNull
    private Integer shippingId;
}
