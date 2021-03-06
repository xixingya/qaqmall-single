package tech.xixing.qaqmall.dao;

import org.apache.ibatis.annotations.Param;
import tech.xixing.qaqmall.pojo.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);
    int batchInsert(@Param("orderItemList")List<OrderItem> orderItemList);

    int updateByPrimaryKeySelective(OrderItem record);

    List<OrderItem> selectByOrderNoSet(@Param("orderNoSet") Set orderNoSet);

    int updateByPrimaryKey(OrderItem record);
}