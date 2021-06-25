package com.cif.security;

import com.cif.utils.BadRequestException;
import com.cif.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 获取当前登录的用户
 * @author Zheng Jie
 * @date 2019-01-17
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, currentUserName);
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

}
