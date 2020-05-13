package tech.xixing.qaqmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.xixing.qaqmall.interceptor.UserLoginInterceptor;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/4 10:05
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login","user/register","/products","/categories","/products/*","/error");
    }
}
