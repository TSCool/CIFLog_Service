package com.cif.security;

import com.cif.domain.PermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.domain.UserBean;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import com.cif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RolesService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean user = userService.getUserByName(username); //获得用户
        if (user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //权限列表
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //根据用户名获取角色列表
        Set<RolesBean> roles = roleService.findRoleByUserId(user.getId());
        for (RolesBean role:roles){
            //循环角色，将角色名称放入到权限列表中
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getLoginPassword(), authorities);

    }
}
