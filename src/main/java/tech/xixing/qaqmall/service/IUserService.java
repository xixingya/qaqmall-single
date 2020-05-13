package tech.xixing.qaqmall.service;

import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 8:46
 */
public interface IUserService {

    /**
     * 注册
     * @param user
     */
    ResponseVO register(User user);
    ResponseVO<User> login(String username,String password);

}
