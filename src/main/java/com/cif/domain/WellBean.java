/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

/**
 * 井
 * @author yuanye
 */
public class WellBean {
    private int id;
    private String name;
    private int workSpaceID;
    private Integer owner;
    private String wellProperties = null;

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

    public int getWorkSpaceID() {
        return workSpaceID;
    }

    public void setWorkSpaceID(int workSpaceID) {
        this.workSpaceID = workSpaceID;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getWellProperties() {
        return wellProperties;
    }

    public void setWellProperties(String wellProperties) {
        this.wellProperties = wellProperties;
    }

    @Override
    public String toString() {
        return "WellBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"workSpaceID\":" + workSpaceID +
                ", \"owner\":" + owner +
                ", \"wellProperties\":'" + wellProperties + '\'' +
                '}';
    }
}
