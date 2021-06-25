package com.cif.security.bean;

import com.cif.domain.UserBean;
import com.cif.service.UserService;
import com.cif.utils.token.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 处理登录验证成功的类
 * @author zhoukebo
 * @date 2018/9/4
 */
@Component
public class FuryAuthSuccessHandler implements AuthenticationSuccessHandler {
    /**Json转化工具*/
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User userDetails = (User)authentication.getPrincipal();

        UserBean user = userService.getUserByName(userDetails.getUsername());

        String token= TokenUtil.sign(new UserBean(userDetails.getUsername(),user.getLoginPassword()));
        user.setToken(token);

        HashMap<String,Object> data=new HashMap<>();
        data.put("userInfo",user);

        System.out.println("管理员 " + userDetails.getUsername() + " 登录");
        Map<String,Object> map=new HashMap<>(2);
        map.put("status", "200");
        map.put("msg", "登录成功");
        map.put("data",data);

//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        HttpSession session = request.getSession();
//        session.setAttribute("SPRING_SECURITY-CONTEXT",SecurityContextHolder.getContext());
//        session.setAttribute("test","hello");

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }

}
