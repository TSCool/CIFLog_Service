package com.cif.domain;

public class UserRolesBean {

    private int id;
    private int userID;
    private int roleID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return "user_roles{" +
                "id=" + id +
                ", userID=" + userID +
                ", roleID=" + roleID +
                '}';
    }
}
