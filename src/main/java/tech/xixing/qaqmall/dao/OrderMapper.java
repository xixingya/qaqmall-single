package tech.xixing.qaqmall.dao;

import tech.xixing.qaqmall.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);
    List<Order> selectByUid(Integer uid);

    int updateByPrimaryKeySelective(Order record);

    Order selectByOrderNo(Long orderNo);

    int updateByPrimaryKey(Order record);
}