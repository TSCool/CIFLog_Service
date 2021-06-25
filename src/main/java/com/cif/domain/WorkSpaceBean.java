/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Set;

/**
 * 工区
 * @author yuanye
 */
public class WorkSpaceBean {
    private int id;
    private String name;
    private Integer owner;

//    @ManyToMany
//    @ApiModelProperty(value = "用户")
//    @JoinTable(name = "workspace_roles",
//            joinColumns = {@JoinColumn(name = "workspaceID",referencedColumnName = "workspaceID")},
//            inverseJoinColumns = {@JoinColumn(name = "userID",referencedColumnName = "userID")})
//    private Set<UserBean> userBeans;
//
//    @ApiModelProperty(value = "角色")
//    @JoinTable(name = "workspace_roles",
//            joinColumns = {@JoinColumn(name = "workspaceID",referencedColumnName = "workspaceID")},
//            inverseJoinColumns = {@JoinColumn(name = "roleID",referencedColumnName = "roleID")})
//    private Set<RolesBean> rolesBeans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

//    public Set<RolesBean> getRolesBeans() {
//        return rolesBeans;
//    }
//
//    public void setRolesBeans(Set<RolesBean> rolesBeans) {
//        this.rolesBeans = rolesBeans;
//    }
//
//    public Set<UserBean> getUserBeans() {
//        return userBeans;
//    }
//
//    public void setUserBeans(Set<UserBean> userBeans) {
//        this.userBeans = userBeans;
//    }


    @Override
    public String toString() {
        return "WorkSpaceBean{" +
                "\"id\"：" + id +
                ", \"name\"：'" + name + '\'' +
                ", \"owner\"：" + owner +
                '}';
    }
}
