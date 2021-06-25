package com.cif.intercept.config;

import com.cif.intercept.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private TokenInterceptor tokenInterceptor;

    //构造方法
    public WebConfiguration(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //添加token拦截器
        InterceptorRegistration tokenInterceptorRegistration = registry.addInterceptor(new TokenInterceptor());
        tokenInterceptorRegistration.addPathPatterns("/**");
        tokenInterceptorRegistration.excludePathPatterns("/error");
        tokenInterceptorRegistration.excludePathPatterns("/static/**");
        tokenInterceptorRegistration.excludePathPatterns("/user/login");
        tokenInterceptorRegistration.excludePathPatterns("/swagger-ui.html");
        tokenInterceptorRegistration.excludePathPatterns("/webjars/**");
        tokenInterceptorRegistration.excludePathPatterns("/swagger-resources/**");
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/user/register");  //登录
        excludePath.add("/user/login");     //注册
        excludePath.add("/static/**");  //静态资源
        excludePath.add("/assets/**");  //静态资源
        excludePath.add("/**");  //静态资源

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
