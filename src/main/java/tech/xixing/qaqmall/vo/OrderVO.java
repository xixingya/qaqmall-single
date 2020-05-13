package tech.xixing.qaqmall.vo;

import lombok.Data;
import tech.xixing.qaqmall.pojo.Shipping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 10:22
 */
@Data
public class OrderVO {
    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;

    private Integer postage;

    private Integer status;

    private String statusDesc;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVO> orderItemVoList;

    private Integer shippingId;

    private Shipping shippingVo;
}
