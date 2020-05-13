package tech.xixing.qaqmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tech.xixing.qaqmall.dao.UserMapper;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.enums.RoleEnum;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.IUserService;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.nio.charset.StandardCharsets;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 8:48
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseVO register(User user) {
        user.setRole(RoleEnum.CUSTOMER.getCode());
        int countByUserName=userMapper.countByUserName(user.getUsername());
        if(countByUserName>0){

            return ResponseVO.error(ResponseEnum.USER_EXIST);
        }
        int countEmail=userMapper.countByEmail(user.getEmail());
        if(countEmail>0){
            return ResponseVO.error(ResponseEnum.EMAIL_EXITS);
        }
        String md5Passwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5Passwd);

        //写入数据库
        int i = userMapper.insertSelective(user);
        if(i==0){
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        return ResponseVO.success();

    }

    @Override
    public ResponseVO<User> login(String username,String password) {
        User user = userMapper.selectByUserName(username);
        if(user==null){
            return ResponseVO.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if(!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return ResponseVO.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword(null);


        return ResponseVO.successByData(user);
    }
}
