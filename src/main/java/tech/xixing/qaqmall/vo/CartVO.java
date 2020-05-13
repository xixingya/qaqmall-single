package tech.xixing.qaqmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/7 11:01
 */
@Data
public class CartVO {

    private List<CartProductVO> cartProductVoList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
