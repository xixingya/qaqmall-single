package tech.xixing.qaqmall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/5 8:18
 */
@Data
public class CategoryVO {
    private Integer id;

    private Integer parentId;

    private String name;


    private Integer sortOrder;

    private List<CategoryVO> subCategories;
}
