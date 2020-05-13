package tech.xixing.qaqmall.dao;

import tech.xixing.qaqmall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUserName(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUserName(String userName);

    int countByEmail(String email);
}