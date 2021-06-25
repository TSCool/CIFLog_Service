package com.cif.domain;

public class RolePermissionsBean {
    private int id;
    private int roleID;
    private int permissionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "rolePermissionsBean{" +
                "id=" + id +
                ", roleID=" + roleID +
                ", permissionId=" + permissionId +
                '}';
    }
}
