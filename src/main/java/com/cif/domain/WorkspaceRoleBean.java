package com.cif.domain;

public class WorkspaceRoleBean {

    private int id;
    private int workspaceID;
    private int userID;
    private int roleID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkspaceID() {
        return workspaceID;
    }

    public void setWorkspaceID(int workspaceID) {
        this.workspaceID = workspaceID;
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
        return "WorkspaceRoleBean{" +
                "id=" + id +
                ", workspaceID=" + workspaceID +
                ", userID=" + userID +
                ", roleID=" + roleID +
                '}';
    }
}
