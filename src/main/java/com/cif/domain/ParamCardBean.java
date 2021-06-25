/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

/**
 * 参数卡片
 * @author yuanye
 */
public class ParamCardBean {
    private int id;
    private String name;
    private int categoryID;
    private Integer owner;
    private String file;

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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ParamCardBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"categoryID\":" + categoryID +
                ", \"owner\":" + owner +
                ", \"file\":'" + file + '\'' +
                '}';
    }
}
