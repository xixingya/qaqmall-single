package tech.xixing.qaqmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 8:54
 */
@Data
public class ProductVO {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;
    public ProductVO(){

    }

    public ProductVO(Integer id, Integer categoryId, String name, String subtitle, String mainImage, Integer status, BigDecimal price) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.mainImage = mainImage;
        this.status = status;
        this.price = price;
    }
}
