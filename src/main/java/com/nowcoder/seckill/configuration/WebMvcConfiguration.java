package com.nowcoder.seckill.configuration;

import com.nowcoder.seckill.controller.Interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/logout");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/status");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/follow");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/unfollow");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/getfollowing");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/user/getfollower");

        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/share/publish");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/share/allmypublish");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/share/friendspublish");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/share/deletepublish");

        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/filetransfer/newShareImg");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/filetransfer/uploadprofilepic");
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/filetransfer/addShareImg");
    }

}
