package tech.xixing.qaqmall.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.exception.UserLoginException;
import tech.xixing.qaqmall.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/4 9:59
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true 表示继续 ，false表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandler..");
        User user=(User) request.getSession().getAttribute(MallConst.CURRENT_USER);

        if(user==null){
            log.info("user=null");
            throw new UserLoginException();
            //ResponseVO.error(ResponseEnum.NEED_LOGIN);
        }

        return true;
    }
}
