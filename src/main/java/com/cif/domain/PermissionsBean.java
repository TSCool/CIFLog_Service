package com.cif.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

/**
 * 权限
 */
public class PermissionsBean {
    private int id;
    private String guardName;
    private String name;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "permissionsBeans")
//    @ApiModelProperty(value = "角色", hidden = true)
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

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

//    public Set<RolesBean> getRolesBeans() {
//        return rolesBeans;
//    }
//
//    public void setRolesBeans(Set<RolesBean> rolesBeans) {
//        this.rolesBeans = rolesBeans;
//    }
}
