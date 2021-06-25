package com.cif.security;

import com.cif.security.bean.FuryAuthFailureHandler;
import com.cif.security.bean.FuryAuthSuccessHandler;
import com.cif.security.bean.MyLogoutSuccessHandler;
import com.cif.security.bean.RestAuthAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 依赖注入自定义的登录成功处理器
     */
    @Autowired
    private FuryAuthSuccessHandler furyAuthSuccessHandler;
//    /**
//     * 依赖注入自定义的登录失败处理器
//     */
//    @Autowired
//    private FuryAuthFailureHandler furyAuthFailureHandler;
//    /**
//     * 依赖注入自定义的注销成功的处理器
//     */
//    @Autowired
//    private MyLogoutSuccessHandler myLogoutSuccessHandler;


//    /**
//     * 注册没有权限的处理器
//     */
//    @Autowired
//    private RestAuthAccessDeniedHandler restAuthAccessDeniedHandler;
//
//    /***注入自定义的CustomPermissionEvaluator*/
//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        return handler;
//    }

    /***注入我们自己的登录逻辑验证器AuthenticationProvider*/
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 配置spring security的控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //"/login"不进行权限验证
                .antMatchers("/user").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/ws/**").hasAuthority("rwx")
                .anyRequest().permitAll()   //其他的需要登陆后才能访问
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                //loginProcessingUrl用于指定前后端分离的时候调用后台登录接口的名称
                .loginProcessingUrl("/user/login")
                //配置登录成功的自定义处理类
                .successHandler(furyAuthSuccessHandler)
                //配置登录失败的自定义处理类
//                .failureHandler(furyAuthFailureHandler)
                .and()
                //loginProcessingUrl用于指定前后端分离的时候调用后台注销接口的名称
                .logout().logoutUrl("/logout")
//                .logoutSuccessHandler(myLogoutSuccessHandler)
                .and()
                //配置没有权限的自定义处理类
//                .exceptionHandling().accessDeniedHandler(restAuthAccessDeniedHandler)
//                .and()
                .cors()//新加入
                .and()
                .csrf().disable();// 取消跨站请求伪造防护
    }

////    @Bean
////    UserDetailsService UserDetailsServiceImpl(){ //注册UserDetailsService 的bean
////        return new UserDetailsServiceImpl();
////    }
////构造一个内存框架对象，获取数据库中的数据
////    @Bean
////    public UserDetailsService myUserDetailsService(){
////        return new UserDetailsServiceImpl();
////    }
////
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        //获取数据库中的数据
////        auth.userDetailsService(myUserDetailsService());
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//
//                // 基于token，不需要csrf
//                .csrf().disable()
//
//                // 基于token，不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//                // 下面开始设置权限
//                .authorizeRequests()
//
//                // 需要登录
////                .antMatchers("/ws/**").hasAuthority("rwx")//超级管理员 -- 所有操作
////                .antMatchers("/ws/**").hasRole("rw")//管理员 -- 读、写
////                .antMatchers("/ws/all/**").hasRole("r")//普通用户 -- 部分可以读
//
//                // 除上面外的所有请求全部放开
//                .anyRequest().permitAll();
//        http.formLogin();
//    }

}
