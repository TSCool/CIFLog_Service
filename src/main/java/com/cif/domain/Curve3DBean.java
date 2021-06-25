/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.domain;

import com.cif.utils.constant.Global;

/**
 *
 * @author yuanye
 */
public class Curve3DBean {

    private int id;
    private String name;
    private int categoryID;
    private Integer owner;
    private double startDepth;
    private double endDepth;
    private double depthLevel;
    private double leftScale;
    private double rightScale;
    private byte dataType = Global.DATA_FLOAT;
    private String curveUnit = "";
    private String depthUnit = "";
    private String propertiesEX = "";
    private String file = null;

    private int timeSampleNum;
    private double timeSampleStartTime;
    private double timeSampleLevel;
    private String timeSampleUnit;

    private int arrayNum;
    private int subCurveType;

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

    public double getStartDepth() {
        return startDepth;
    }

    public void setStartDepth(double startDepth) {
        this.startDepth = startDepth;
    }

    public double getEndDepth() {
        return endDepth;
    }

    public void setEndDepth(double endDepth) {
        this.endDepth = endDepth;
    }

    public double getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(double depthLevel) {
        this.depthLevel = depthLevel;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public String getCurveUnit() {
        return curveUnit;
    }

    public void setCurveUnit(String curveUnit) {
        this.curveUnit = curveUnit;
    }

    public String getDepthUnit() {
        return depthUnit;
    }

    public void setDepthUnit(String depthUnit) {
        this.depthUnit = depthUnit;
    }

    public double getLeftScale() {
        return leftScale;
    }

    public void setLeftScale(double leftScale) {
        this.leftScale = leftScale;
    }

    public double getRightScale() {
        return rightScale;
    }

    public void setRightScale(double rightScale) {
        this.rightScale = rightScale;
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

    public int getTimeSampleNum() {
        return timeSampleNum;
    }

    public void setTimeSampleNum(int timeSampleNum) {
        this.timeSampleNum = timeSampleNum;
    }

    public double getTimeSampleStartTime() {
        return timeSampleStartTime;
    }

    public void setTimeSampleStartTime(double timeSampleStartTime) {
        this.timeSampleStartTime = timeSampleStartTime;
    }

    public double getTimeSampleLevel() {
        return timeSampleLevel;
    }

    public void setTimeSampleLevel(double timeSampleLevel) {
        this.timeSampleLevel = timeSampleLevel;
    }

    public String getTimeSampleUnit() {
        return timeSampleUnit;
    }

    public void setTimeSampleUnit(String timeSampleUnit) {
        this.timeSampleUnit = timeSampleUnit;
    }

    public int getArrayNum() {
        return arrayNum;
    }

    public void setArrayNum(int arrayNum) {
        this.arrayNum = arrayNum;
    }

    public int getSubCurveType() {
        return subCurveType;
    }

    public void setSubCurveType(int subCurveType) {
        this.subCurveType = subCurveType;
    }

    @Override
    public String toString() {
        return "Curve3DBean{" +
                "\"id\":" + id +
                ", \"name\":'" + name + '\'' +
                ", \"categoryID\":" + categoryID +
                ", \"owner\":" + owner +
                ", \"startDepth\":" + startDepth +
                ", \"endDepth\":" + endDepth +
                ", \"depthLevel\":" + depthLevel +
                ", \"leftScale\":" + leftScale +
                ", \"rightScale\":" + rightScale +
                ", \"dataType\":" + dataType +
                ", \"curveUnit\":'" + curveUnit + '\'' +
                ", \"depthUnit\":'" + depthUnit + '\'' +
                ", \"propertiesEX\":'" + propertiesEX + '\'' +
                ", \"file\":'" + file + '\'' +
                ", \"timeSampleNum\":" + timeSampleNum +
                ", \"timeSampleStartTime\":" + timeSampleStartTime +
                ", \"timeSampleLevel\":" + timeSampleLevel +
                ", \"timeSampleUnit\":'" + timeSampleUnit + '\'' +
                ", \"arrayNum\":" + arrayNum +
                ", \"subCurveType\":" + subCurveType +
                '}';
    }
}
