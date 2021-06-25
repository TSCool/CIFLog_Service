package com.cif.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色
 */
public class RolesBean {

    private int id;
    private String name;

    @ManyToMany
    @ApiModelProperty(value = "角色权限")
    @JoinTable(name = "role_permissions",
            joinColumns = {@JoinColumn(name = "roleID",referencedColumnName = "roleID")},
            inverseJoinColumns = {@JoinColumn(name = "permissionId",referencedColumnName = "permissionId")})
    private Set<PermissionsBean> permissionsBeans;

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

    public Set<PermissionsBean> getPermissionsBeans() {
        return permissionsBeans;
    }

    public void setPermissionsBeans(Set<PermissionsBean> permissionsBeans) {
        this.permissionsBeans = permissionsBeans;
    }

}
