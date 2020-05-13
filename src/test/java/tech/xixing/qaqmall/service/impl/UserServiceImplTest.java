package tech.xixing.qaqmall.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.enums.RoleEnum;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.IUserService;


/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 9:16
 */
public class UserServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    IUserService userService;
    @Test
    public void testReg(){
        User user =new User("jack","123456","123@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);

    }

}