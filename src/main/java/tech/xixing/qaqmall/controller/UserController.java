package tech.xixing.qaqmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.form.UserLoginForm;
import tech.xixing.qaqmall.form.UserRegisterForm;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.IUserService;
import tech.xixing.qaqmall.vo.ResponseVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 14:50
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/user/register")
    //@RequestParam String username 适用与form-url-encode
    //@RequestBody 适用于 application/json
    public ResponseVO<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm
                               ){

        User user=new User();
        BeanUtils.copyProperties(userRegisterForm,user);

        userService.register(user);
        log.info("username={}", userRegisterForm.getUsername());


        return ResponseVO.success();
    }
    @PostMapping("/user/login")
    public ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpServletRequest httpServletRequest){
        ResponseVO<User> responseVO = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(MallConst.CURRENT_USER,responseVO.getData());

        return responseVO;

    }
    //session保存在内存中。改进版本是token加redis
    @GetMapping("/user")
    public ResponseVO<User> getUserInfo(HttpSession httpSession){
        User user=(User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return ResponseVO.successByData(user);
    }

    @PostMapping("/user/logout")
    public ResponseVO logout(HttpSession session){
        log.info("sessionId={}",session.getId());

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVO.success();
    }


}
