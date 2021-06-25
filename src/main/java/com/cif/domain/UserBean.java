/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 用户
 * @author yuanye
 */
public class UserBean {
    private int id;
    private String name;
    private String loginPassword;
    private String submitPassword;
    private String token;

    @ManyToMany
    @ApiModelProperty(value = "用户角色")
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "userID",referencedColumnName = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "roleID",referencedColumnName = "roleID")})
    private Set<RolesBean> roles;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "users")
//    @ApiModelProperty(value = "工区", hidden = true)
//    private Set<WorkSpaceBean> workSpaceBeans;

    public UserBean(){

    }

    public UserBean(String name, String loginPassword) {
        this.name = name;
        this.loginPassword = loginPassword;
    }

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

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getSubmitPassword() {
        return submitPassword;
    }

    public void setSubmitPassword(String submitPassword) {
        this.submitPassword = submitPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesBean> roles) {
        this.roles = roles;
    }

//    public Set<WorkSpaceBean> getWorkSpaceBeans() {
//        return workSpaceBeans;
//    }
//
//    public void setWorkSpaceBeans(Set<WorkSpaceBean> workSpaceBeans) {
//        this.workSpaceBeans = workSpaceBeans;
//    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", submitPassword='" + submitPassword + '\'' +
                '}';
    }
}
