package tech.xixing.qaqmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 9:44
 */
@Data
public class ProductDetailVO {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
