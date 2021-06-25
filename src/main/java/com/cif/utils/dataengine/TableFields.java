/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.dataengine;

import com.cif.utils.baseutil.array.ValueArray;
import com.cif.utils.regulation.Regulation;

import java.util.ArrayList;

/**
 *
 * @author think
 */
public class TableFields {

    private int fieldNum = 0;
    private ArrayList<String> fieldNames;
    private ArrayList<String> fieldUnits;
    private ArrayList<Byte> dataTypes;
    private ArrayList<ValueArray> defaultValues;
    private ArrayList<String> enumStrings;
    private ArrayList<Integer> dataNums;
    private String templateName;

    public TableFields() {
        init(0);
    }

    public void setFieldNum(int fieldNum) {
        this.fieldNum = fieldNum;
    }

    public ArrayList<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(ArrayList<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public ArrayList<String> getFieldUnits() {
        return fieldUnits;
    }

    public void setFieldUnits(ArrayList<String> fieldUnits) {
        this.fieldUnits = fieldUnits;
    }

    public ArrayList<Byte> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(ArrayList<Byte> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public ArrayList<ValueArray> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(ArrayList<ValueArray> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public ArrayList<String> getEnumStrings() {
        return enumStrings;
    }

    public void setEnumStrings(ArrayList<String> enumStrings) {
        this.enumStrings = enumStrings;
    }

    public ArrayList<Integer> getDataNums() {
        return dataNums;
    }

    public void setDataNums(ArrayList<Integer> dataNums) {
        this.dataNums = dataNums;
    }

    public TableFields(int fieldNum) {
        init(fieldNum);
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    /**
     * 初始化表格字段
     *
     * @param fieldNum 字段数
     */
    public final void init(int fieldNum) {
        this.fieldNum = fieldNum;
        fieldNames = new ArrayList<String>();
        fieldUnits = new ArrayList<String>();
        dataTypes = new ArrayList<Byte>();
        defaultValues = new ArrayList<ValueArray>();
        enumStrings = new ArrayList<String>();
        dataNums = new ArrayList<Integer>();
        for (int i = 0; i < fieldNum; i++) {
            fieldNames.add(null);
            fieldUnits.add(null);
            dataTypes.add((byte) 1);
            defaultValues.add(null);
            enumStrings.add(null);
            dataNums.add(1);
        }
    }

    public void clearAll() {
        fieldNames.clear();
        fieldUnits.clear();
        dataTypes.clear();
        defaultValues.clear();
        enumStrings.clear();
        dataNums.clear();
        fieldNum = 0;
    }

    /**
     * 获取字段名为fileName的字段序号
     *
     * @param fieldName 字段名
     * @return 字段序号
     */
    public int getFieldIndex(String fieldName) {
        for (int i = 0; i < fieldNum; i++) {
            if (fieldName.equals(this.fieldNames.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 设置字段名
     *
     * @param index 字段序号
     * @param fieldName 字段名称
     */
    public void setName(int index, String fieldName) {
        this.fieldNames.set(index, fieldName);
    }

    /**
     * 设置字段单位
     *
     * @param index 字段序号
     * @param fieldUnit 字段单位
     */
    public void setUnit(int index, String fieldUnit) {
        this.fieldUnits.set(index, fieldUnit);
    }

    /**
     * 设置数据类型
     *
     * @param index 字段序号
     * @param dataType 字段类型
     */
    public void setDataType(int index, byte dataType) {
        this.dataTypes.set(index, dataType);
    }

    /**
     * 设置字段缺省值
     *
     * @param index 字段序号
     * @param defaultValue 缺省值
     */
    public void setDefaultValue(int index, ValueArray defaultValue) {
        defaultValues.remove(index);
        defaultValues.add(index, defaultValue);
    }

    /**
     * 设置列表枚举字符串
     *
     * @param index 字段序号
     * @param enumString 枚举字符串，各列表项用“|”分隔
     */
    public void setEnumString(int index, String enumString) {
        this.enumStrings.set(index, enumString);
    }

    /**
     * 获取字段名
     *
     * @param index 字段序号
     * @return 字段名
     */
    public String getName(int index) {
        return fieldNames.get(index);
    }

    /**
     * 获取字段显示名称
     *
     * @param index 字段序号
     * @return 字段显示名称
     */
    public String getDisplayName(int index) {
        if (templateName == null) {
            return getName(index);
        }
        return Regulation.getTableDefault().getFieldName(templateName, getName(index));
    }

    /**
     * 获取字段单位
     *
     * @param index 字段序号
     * @return 字段单位
     */
    public String getUnit(int index) {
        return fieldUnits.get(index);
    }

    /**
     * 获取数据类型
     *
     * @param index 字段序号
     * @return 字段序号
     */
    public byte getDataType(int index) {
        return dataTypes.get(index);
    }

    /**
     * 获取字段缺省值
     *
     * @param index 字段序号
     * @return 字段缺省值
     */
    public ValueArray getDefaultValue(int index) {
        return defaultValues.get(index);
    }

    /**
     * 获取列表枚举字符串
     *
     * @param index 字段序号
     * @return 列表枚举字符串
     */
    public String getEnumString(int index) {
        return enumStrings.get(index);
    }

    /**
     * 获取字段总数
     *
     * @return 字段总数
     */
    public int getFieldNum() {
        return fieldNum;
    }

    /**
     * 获得指定下标的表格字段数据长度
     *
     * @param index 下标号
     * @return 表格字段数据长度
     */
    public int getDataNum(int index) {
        return dataNums.get(index);
    }

    /**
     * 设置指定下标的表格字段数据长度
     *
     * @param index 下标号
     * @param dataNum 表格字段数据长度
     */
    public void setDataNum(int index, int dataNum) {
        this.dataNums.set(index, dataNum);
    }

    public void removeField(int index) {
        fieldNames.remove(index);
        fieldUnits.remove(index);
        dataTypes.remove(index);
        defaultValues.remove(index);
        enumStrings.remove(index);
        dataNums.remove(index);
        fieldNum--;
    }

    public void addFiled(int index, String name, String unit, byte dataType) {
        fieldNames.add(index, name);
        fieldUnits.add(index, unit);
        dataTypes.add(index, dataType);
        defaultValues.add(index, null);
        enumStrings.add(index, null);
        dataNums.add(index, 1);
        fieldNum++;
    }

    public TableFields duplicate() {
        TableFields tf = new TableFields();
        tf.fieldNum = this.fieldNum;
        tf.fieldNames = new ArrayList<String>();
        tf.fieldUnits = new ArrayList<String>();
        tf.dataTypes = new ArrayList<Byte>();
        tf.defaultValues = new ArrayList<ValueArray>();
        tf.enumStrings = new ArrayList<String>();
        tf.dataNums = new ArrayList<Integer>();
        for (int i = 0; i < fieldNum; i++) {
            tf.fieldNames.add(this.fieldNames.get(i));
            tf.fieldUnits.add(this.fieldUnits.get(i));
            tf.dataTypes.add(this.dataTypes.get(i));
            tf.defaultValues.add(this.defaultValues.get(i));
            tf.enumStrings.add(this.enumStrings.get(i));
            tf.dataNums.add(this.dataNums.get(i));
        }
        return tf;
    }

    @Override
    public String toString() {
        return "TableFields{" +
                "\"fieldNum\":" + fieldNum +
                ", \"fieldNames\":" + fieldNames +
                ", \"fieldUnits\":" + fieldUnits +
                ", \"dataTypes\":" + dataTypes +
                ", \"defaultValues\":" + defaultValues +
                ", \"enumStrings\":" + enumStrings +
                ", \"dataNums\":" + dataNums +
                ", \"templateName\":'" + templateName + '\'' +
                '}';
    }
}
