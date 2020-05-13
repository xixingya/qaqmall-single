package tech.xixing.qaqmall.dao;

import org.apache.ibatis.annotations.Param;
import tech.xixing.qaqmall.pojo.Product;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    int updateByListPrimaryKey(@Param("productList") List<Product> productList);

    List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);
    List<Product> selectByPrimaryKeySet(@Param("primaryKeySet") Set<Integer> primaryKeySet);
}