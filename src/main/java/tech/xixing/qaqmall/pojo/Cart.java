package tech.xixing.qaqmall.pojo;

import lombok.Data;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 9:37
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public Cart(){

    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
