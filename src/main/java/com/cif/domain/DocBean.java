/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

/**
 * 文档
 * @author yuanye
 */
public class DocBean {

    private int id;
    private String name;
    private long size;
    private int categoryID;
    private Integer owner;
    private String file = null;

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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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
        return "DocBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"size\":" + size +
                ", \"categoryID\":" + categoryID +
                ", \"owner\":" + owner +
                ", \"file\":'" + file + '\'' +
                '}';
    }
}
