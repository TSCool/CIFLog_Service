/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

import com.cif.utils.constant.Global;

/**
 * 井次
 * @author yuanye
 */
public class CategoryBean {
    private int id;
    private String name;
    private int wellID;
    private Integer owner;
    private double categoryDepthLevel;
    private double categoryStartDepth;
    private double categoryEndDepth;
    private String categoryDepthUnit = Global.DEPTH_METER;
    
    //category properties
    private String company;
    private String team;
    private String project;
    private String creator;
    private int loggingNo;//井次
    private long date;

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

    public int getWellID() {
        return wellID;
    }

    public void setWellID(int wellID) {
        this.wellID = wellID;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public double getCategoryDepthLevel() {
        return categoryDepthLevel;
    }

    public void setCategoryDepthLevel(double categoryDepthLevel) {
        this.categoryDepthLevel = categoryDepthLevel;
    }

    public double getCategoryStartDepth() {
        return categoryStartDepth;
    }

    public void setCategoryStartDepth(double categoryStartDepth) {
        this.categoryStartDepth = categoryStartDepth;
    }

    public double getCategoryEndDepth() {
        return categoryEndDepth;
    }

    public void setCategoryEndDepth(double categoryEndDepth) {
        this.categoryEndDepth = categoryEndDepth;
    }

    public String getCategoryDepthUnit() {
        return categoryDepthUnit;
    }

    public void setCategoryDepthUnit(String categoryDepthUnit) {
        this.categoryDepthUnit = categoryDepthUnit;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getLoggingNo() {
        return loggingNo;
    }

    public void setLoggingNo(int loggingNo) {
        this.loggingNo = loggingNo;
    }


    @Override
    public String toString() {
        return "CategoryBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"wellID\":" + wellID +
                ", \"owner\":" + owner +
                ", \"categoryDepthLevel\":" + categoryDepthLevel +
                ", \"categoryStartDepth\":" + categoryStartDepth +
                ", \"categoryEndDepth\":" + categoryEndDepth +
                ", \"categoryDepthUnit\":'" + categoryDepthUnit + '\'' +
                ", \"company\":'" + company + '\'' +
                ", \"team\":'" + team + '\'' +
                ", \"project\":'" + project + '\'' +
                ", \"creator\":'" + creator + '\'' +
                ", \"loggingNo\":" + loggingNo +
                ", \"date\":" + date +
                '}';
    }
}
