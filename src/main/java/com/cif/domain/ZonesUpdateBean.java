package com.cif.domain;

import java.util.Arrays;

public class ZonesUpdateBean {

    private String zoneType;
    private ZoneItem[] zoneItems;
    private boolean clear;
    private String workspaceId;

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

    public ZoneItem[] getZoneItems() {
        return zoneItems;
    }

    public void setZoneItems(ZoneItem[] zoneItems) {
        this.zoneItems = zoneItems;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public String toString() {
        return "ZonesUpdateBean{" +
                "\"zoneType\":'" + zoneType + '\'' +
                ", \"zoneItems\":" + Arrays.toString(zoneItems) +
                ", \"clear\":" + clear +
                ", \"workspaceId\":'" + workspaceId + '\'' +
                '}';
    }
}
