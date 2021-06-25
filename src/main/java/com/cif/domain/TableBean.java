/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

import com.cif.utils.constant.Global;

/**
 * 表格
 * @author yuanye
 */
public class TableBean {
    private int id;
    private String name;
    private int categoryID;
    private Integer owner;
    private String propertiesEX = "";
    private String file = null;
    private byte loggingType = Global.LOGGING_COMMON_TABLE;
    
    private int rowCount = 0;
    private int columnCount = 0;
    private String templateName = null;

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

    public String getPropertiesEX() {
        return propertiesEX;
    }

    public void setPropertiesEX(String propertiesEX) {
        this.propertiesEX = propertiesEX;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public byte getLoggingType() {
        return loggingType;
    }

    public void setLoggingType(byte loggingType) {
        this.loggingType = loggingType;
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"categoryID\":" + categoryID +
                ", \"owner\":" + owner +
                ", \"propertiesEX\":'" + propertiesEX + '\'' +
                ", \"file\":'" + file + '\'' +
                ", \"loggingType\":" + loggingType +
                ", \"rowCount\":" + rowCount +
                ", \"columnCount\":" + columnCount +
                ", \"templateName\":'" + templateName + '\'' +
                '}';
    }
}
