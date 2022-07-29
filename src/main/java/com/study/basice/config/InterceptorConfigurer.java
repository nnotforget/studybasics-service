package com.study.basice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyk
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns():添加需要拦截的路径
     * excludePathPatterns():添加不需要拦截的路径
     */
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义排除swagger访问的路径配置
        String[] swaggerExcludes = new String[]{"/swagger-ui.html", "/swagger-resources/**", "/webjars/**"};

        // 不包含的地址
        List<String> excludePathList1 = new ArrayList<String>();
        excludePathList1.add("/login/*");
        excludePathList1.add("/validate/*");

        excludePathList1.add("/**.html");
        excludePathList1.add("/**.ftl");
        excludePathList1.add("/**.css");
        excludePathList1.add("/**.js");
        // 注册登录校验拦截器
        InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(new UserLoginInterceptor());
        // 拦截地址集合
        interceptorRegistration1.addPathPatterns("/**");
        // 不拦截地址集合
        interceptorRegistration1.excludePathPatterns(excludePathList1).excludePathPatterns(swaggerExcludes);
    }

}
