package tech.xixing.qaqmall.dao;

import org.apache.ibatis.annotations.Param;
import tech.xixing.qaqmall.pojo.Shipping;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    List<Shipping> selectByIdSet(@Param("idSet") Set idSet);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("shippingId") Integer shippingId,@Param("uid") Integer uid);

    List<Shipping> selectByUid(Integer uid);
    Shipping selectByShippingIdAndUid(@Param("shippingId") Integer shippingId,@Param("uid") Integer uid);
}