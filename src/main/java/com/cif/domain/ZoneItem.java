package com.cif.domain;

import com.cif.utils.constant.Global;

import java.awt.*;

public class ZoneItem {
    private String wellID;         //井号
    private String zoneName;  //层号
    private double topDep = Global.NULL_DOUBLE_VALUE;      //顶深
    private double bottomDep = Global.NULL_DOUBLE_VALUE;   //底深
    private String description;       //层描述
    private String orgId;
    private Color color;

    public double getBottomDep() {
        return bottomDep;
    }

    public void setBottomDep(double bottomDep) {
        this.bottomDep = bottomDep;
    }

    public double getTopDep() {
        return topDep;
    }

    public void setTopDep(double topDep) {
        this.topDep = topDep;
    }

    public String getWellID() {
        return wellID;
    }

    public void setWellID(String wellID) {
        this.wellID = wellID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void copyFrom(ZoneItem zoneItem) {
        wellID = zoneItem.wellID;         //井号
        zoneName = zoneItem.zoneName;  //层号
        topDep = zoneItem.topDep;      //顶深
        bottomDep = zoneItem.bottomDep;   //底深
        description = zoneItem.description;       //层描述
        orgId = zoneItem.orgId;
        color = zoneItem.color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ZoneItem{" +
                "\"wellID\":'" + wellID + '\'' +
                ", \"zoneName\":'" + zoneName + '\'' +
                ", \"topDep\":" + topDep +
                ", \"bottomDep\":" + bottomDep +
                ", \"description\":'" + description + '\'' +
                ", \"orgId\":'" + orgId + '\'' +
                ", \"color\":" + color +
                '}';
    }
}
