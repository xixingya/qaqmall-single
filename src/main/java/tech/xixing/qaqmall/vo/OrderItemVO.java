package tech.xixing.qaqmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 10:24
 */
@Data
public class OrderItemVO {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;


}
