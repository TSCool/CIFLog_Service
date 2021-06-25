package com.cif.domain;

public class ZonesParentBean {
    private int workspaceID;
    private Zones zones;

    public int getWorkspaceID() {
        return workspaceID;
    }

    public void setWorkspaceID(int workspaceID) {
        this.workspaceID = workspaceID;
    }

    public Zones getZones() {
        return zones;
    }

    public void setZones(Zones zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "ZonesParentBean{" +
                "\"workspaceID\":" + workspaceID +
                ", \"zones\":" + zones +
                '}';
    }
}
