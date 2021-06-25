/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.dataengine;

import com.cif.utils.constant.Global;
import com.cif.utils.baseutil.array.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author think
 */
public final class TableRecords {

    private ArrayList<OneRowRecords> records;
    private TableFields tableFields = null;
    private int recordNum;

    public TableRecords() {
    }

    public ArrayList<OneRowRecords> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<OneRowRecords> records) {
        this.records = records;
    }

    public void setTableFields(TableFields tableFields) {
        this.tableFields = tableFields;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public Comparator<OneRowRecords> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<OneRowRecords> comparator) {
        this.comparator = comparator;
    }

    public TableRecords(TableFields tableFields) {
        this(0, tableFields);
    }

    public TableRecords(int recordNum, TableFields tableFields) {
        init(recordNum, tableFields);
    }

    public TableRecords(TableFields tableFields, Object[][] objs) {
        if (objs == null) {
            init(recordNum, tableFields);
        } else {
            init(objs.length, tableFields);
            String recordData;
            for (int i = 0; i < objs.length; i++) {
                for (int j = 0; j < tableFields.getFieldNum(); j++) {
                    recordData = objs[i][j].toString();
                    switch (tableFields.getDataType(j)) {
                        case Global.DATA_BYTE:
                            try {
                                setRecordByteData(i, j, Byte.parseByte(recordData));
                            } catch (NumberFormatException e) {
                                setRecordByteData(i, j, Global.NULL_BYTE_VALUE);
                            }
                            break;
                        case Global.DATA_SHORT:
                            try {
                                setRecordShortData(i, j, Short.parseShort(recordData));
                            } catch (NumberFormatException e) {
                                setRecordShortData(i, j, Global.NULL_SHORT_VALUE);
                            }
                            break;
                        case Global.DATA_INT:
                            try {
                                setRecordIntData(i, j, Integer.parseInt(recordData));
                            } catch (NumberFormatException e) {
                                setRecordIntData(i, j, Global.NULL_INT_VALUE);
                            }
                            break;
                        case Global.DATA_LONG:
                            try {
                                setRecordLongData(i, j, Long.parseLong(recordData));
                            } catch (NumberFormatException e) {
                                setRecordLongData(i, j, Global.NULL_LONG_VALUE);
                            }
                            break;
                        case Global.DATA_FLOAT:
                            try {
                                setRecordFloatData(i, j, Float.parseFloat(recordData));
                            } catch (NumberFormatException e) {
                                setRecordFloatData(i, j, Global.NULL_FLOAT_VALUE);
                            }
                            break;
                        case Global.DATA_DOUBLE:
                        case Global.DATA_DEPTH:
                            try {
                                setRecordDoubleData(i, j, Double.parseDouble(recordData));
                            } catch (NumberFormatException e) {
                                setRecordDoubleData(i, j, Global.NULL_DOUBLE_VALUE);
                            }
                            break;
                        case Global.DATA_STRING:
                        case Global.DATA_MULTI_STRING:
                        case Global.DATA_LIST:
                        case Global.DATA_CUSTOM_COLOR:
                        case Global.DATA_BITMAP:
                        case Global.DATA_RESULT:
                        case Global.DATA_VECTOR_SYMBOL:
                        case Global.DATA_DEFINED_COLOR:
                            setRecordStringData(i, j, recordData);
                            break;
                    }
                }
            }
        }
    }

    /**
     * 表格记录初始化
     *
     * @param recordNum 记录数
     * @param tableFields 表格字段信息
     */
    public final void init(int recordNum, TableFields tableFields) {
        this.tableFields = tableFields;
        this.recordNum = recordNum;
        records = new ArrayList<OneRowRecords>();
        int fieldCount = tableFields.getFieldNum();
        for (int i = 0; i < recordNum; i++) {
            records.add(new OneRowRecords(fieldCount));
        }
    }

    public void clearAll() {
        records.clear();
        recordNum = 0;
    }

    /**
     * 读取指定行记录
     *
     * @param indexToRead 读取行索引
     * @param readingNumber 读取行数
     * @param tableRecords 表格记录
     */
    public void readTableRecords(int indexToRead, int readingNumber, TableRecords tableRecords) {
        tableRecords.init(0, tableFields);
        tableRecords.recordNum = readingNumber;
        tableRecords.records.clear();
        for (int i = 0; i < readingNumber; i++) {
            if(records.size() != 0){
                tableRecords.records.add(records.get(indexToRead + i).clone());
            }else{
                tableRecords.records.add(new OneRowRecords(this.tableFields.getFieldNum()));
            }
        }
    }

    /**
     * 获取表格字段信息
     *
     * @return 表格字段信息
     */
    public TableFields getTableFields() {
        return tableFields;
    }

    /**
     * 获取表格记录数
     *
     * @return 表格记录数
     */
    public int getRecordsNum() {
        return records.size();
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordByteData(int recordIndex, int fieldIndex, byte[] data) {
        if (data == null) {
            return;
        }
        ByteArray dataArray = new ByteArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordShortData(int recordIndex, int fieldIndex, short[] data) {
        if (data == null) {
            return;
        }
        ShortArray dataArray = new ShortArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordIntData(int recordIndex, int fieldIndex, int[] data) {
        if (data == null) {
            return;
        }
        IntegerArray dataArray = new IntegerArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordLongData(int recordIndex, int fieldIndex, long[] data) {
        if (data == null) {
            return;
        }
        LongArray dataArray = new LongArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordFloatData(int recordIndex, int fieldIndex, float[] data) {
        if (data == null) {
            return;
        }
        FloatArray dataArray = new FloatArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordDoubleData(int recordIndex, int fieldIndex, double[] data) {
        if (data == null) {
            return;
        }
        DoubleArray dataArray = new DoubleArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordStringData(int recordIndex, int fieldIndex, String[] data) {
        if (data == null) {
            return;
        }
        StringArray dataArray = new StringArray(data.length, 10);
        dataArray.setArray(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordStringData(int recordIndex, int fieldIndex, String data) {
        if (data == null) {
            return;
        }
        StringArray dataArray = new StringArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordByteData(int recordIndex, int fieldIndex, byte data) {
        ByteArray dataArray = new ByteArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordShortData(int recordIndex, int fieldIndex, short data) {
        ShortArray dataArray = new ShortArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordIntData(int recordIndex, int fieldIndex, int data) {
        IntegerArray dataArray = new IntegerArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordLongData(int recordIndex, int fieldIndex, long data) {
        LongArray dataArray = new LongArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordFloatData(int recordIndex, int fieldIndex, float data) {
        FloatArray dataArray = new FloatArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);
    }

    /**
     * 设置单元格值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @param data 设置数值
     */
    public void setRecordDoubleData(int recordIndex, int fieldIndex, double data) {
        DoubleArray dataArray = new DoubleArray(1, 10);
        dataArray.add(data);
        records.get(recordIndex).setData(fieldIndex, dataArray);

    }

    //以下get函数中，调用完函数后，data值为null，则在表格中填入空
    public String[] getRecordStringArrayDataForcibly(int recordIndex, int fieldIndex, Integer decimalNumber) {
        String[] result = null;
        int size = 0;
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                byte[] byteArray = ((ByteArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (byteArray != null) {
                    size = byteArray.length;
                    result = new String[size];
                    for (int i = 0; i < size; i++) {
                        result[i] = String.valueOf(byteArray[i]);
                    }
                }
                break;
            case Global.DATA_SHORT:
                short[] shortArray = ((ShortArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (shortArray != null) {
                    size = shortArray.length;
                    result = new String[size];
                    for (int i = 0; i < size; i++) {
                        result[i] = String.valueOf(shortArray[i]);
                    }
                }
                break;
            case Global.DATA_INT:
                int[] intArray = ((IntegerArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (intArray != null) {
                    size = intArray.length;
                    result = new String[size];
                    for (int i = 0; i < size; i++) {
                        result[i] = String.valueOf(intArray[i]);
                    }
                }
                break;
            case Global.DATA_LONG:
                long[] longArray = ((LongArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (longArray != null) {
                    size = longArray.length;
                    result = new String[size];
                    for (int i = 0; i < size; i++) {
                        result[i] = String.valueOf(longArray[i]);
                    }
                }
                break;
            case Global.DATA_FLOAT:
                float[] floatArray = ((FloatArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (floatArray != null) {
                    size = floatArray.length;
                    result = new String[size];
                    if (decimalNumber != null) {
                        String format = "%10." + decimalNumber + "f";
                        for (int i = 0; i < size; i++) {
                            result[i] = String.format(format, floatArray[i]);
                        }
                    } else {
                        for (int i = 0; i < size; i++) {
                            result[i] = String.valueOf(floatArray[i]);
                        }
                    }

//                    if (decimalNumber != null) {
//                        int decimal = decimalNumber.intValue();
//                        NumberFormat numberFormat = NumberFormat.getInstance();
//                        numberFormat.setMaximumFractionDigits(decimal);
//                        numberFormat.setMinimumFractionDigits(decimal);
//
//                        for (int i = 0; i < size; i++) {
//                            result[i] = numberFormat.format(floatArray[i]);
//                        }
//                    } else {
//                        for (int i = 0; i < size; i++) {
//                            result[i] = String.valueOf(floatArray[i]);
//                        }
//                    }
                }
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                double[] doubleArray = ((DoubleArray) records.get(recordIndex).getData(fieldIndex)).getArray();
                if (doubleArray != null) {
                    size = doubleArray.length;
                    result = new String[size];
                    if (decimalNumber != null) {
                        String format = "%10." + decimalNumber + "f";
                        for (int i = 0; i < size; i++) {
                            result[i] = String.format(format, doubleArray[i]);
                        }
                    } else {
                        for (int i = 0; i < size; i++) {
                            result[i] = String.valueOf(doubleArray[i]);
                        }
                    }
//                    if (decimalNumber != null) {
//                        int decimal = decimalNumber.intValue();
//                        NumberFormat numberFormat = NumberFormat.getInstance();
//                        numberFormat.setMaximumFractionDigits(decimal);
//                        numberFormat.setMinimumFractionDigits(decimal);
//
//                        for (int i = 0; i < size; i++) {
//                            result[i] = numberFormat.format(doubleArray[i]);
//                        }
//                    } else {
//                        for (int i = 0; i < size; i++) {
//                            result[i] = String.valueOf(doubleArray[i]);
//                        }
//                    }
                }
                break;
            case Global.DATA_STRING:
            case Global.DATA_MULTI_STRING:
            case Global.DATA_LIST:
            case Global.DATA_DATE:
            case Global.DATA_CUSTOM_COLOR:
            case Global.DATA_BITMAP:
            case Global.DATA_RESULT:
            case Global.DATA_VECTOR_SYMBOL:
            case Global.DATA_DEFINED_COLOR:
                result = getRecordStringArrayData(recordIndex, fieldIndex);
                break;
            default:
                break;
        }
        return result;
    }

    public void setRecordObjectData(int recordIndex, int fieldIndex, Object data) {
        if (data == null) {
            return;
        }
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                setRecordByteData(recordIndex, fieldIndex, (Byte) data);
                break;
            case Global.DATA_SHORT:
                setRecordShortData(recordIndex, fieldIndex, (Short) data);
                break;
            case Global.DATA_INT:
                setRecordIntData(recordIndex, fieldIndex, (Integer) data);
                break;
            case Global.DATA_LONG:
                setRecordLongData(recordIndex, fieldIndex, (Long) data);
                break;
            case Global.DATA_FLOAT:
                setRecordFloatData(recordIndex, fieldIndex, (Float) data);
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                setRecordDoubleData(recordIndex, fieldIndex, (Double) data);
                break;
            case Global.DATA_STRING:
            case Global.DATA_MULTI_STRING:
            case Global.DATA_LIST:
            case Global.DATA_DATE:
            case Global.DATA_CUSTOM_COLOR:
            case Global.DATA_BITMAP:
            case Global.DATA_RESULT:
            case Global.DATA_VECTOR_SYMBOL:
            case Global.DATA_DEFINED_COLOR:
                setRecordStringData(recordIndex, fieldIndex, data.toString());
                break;
            default:
                break;
        }
    }

    public void setRecordStringArrayDataForcibly(int recordIndex, int fieldIndex, String[] data) {
        if (data == null) {
            return;
        }
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                byte[] bs = new byte[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        bs[i] = Byte.parseByte(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordByteData(recordIndex, fieldIndex, bs);
                break;
            case Global.DATA_SHORT:
                short[] ss = new short[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        ss[i] = Short.parseShort(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordShortData(recordIndex, fieldIndex, ss);
                break;
            case Global.DATA_INT:
                int[] is = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        is[i] = Integer.parseInt(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordIntData(recordIndex, fieldIndex, is);
                break;
            case Global.DATA_LONG:
                long[] ls = new long[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        ls[i] = Long.parseLong(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordLongData(recordIndex, fieldIndex, ls);
                break;
            case Global.DATA_FLOAT:
                float[] fs = new float[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        fs[i] = Float.parseFloat(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordFloatData(recordIndex, fieldIndex, fs);
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                double[] ds = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    try {
                        ds[i] = Double.parseDouble(data[i]);
                    } catch (Exception e) {
                    }
                }
                setRecordDoubleData(recordIndex, fieldIndex, ds);
                break;
            case Global.DATA_STRING:
            case Global.DATA_MULTI_STRING:
            case Global.DATA_LIST:
            case Global.DATA_DATE:
            case Global.DATA_CUSTOM_COLOR:
            case Global.DATA_BITMAP:
            case Global.DATA_RESULT:
            case Global.DATA_VECTOR_SYMBOL:
            case Global.DATA_DEFINED_COLOR:
                setRecordStringData(recordIndex, fieldIndex, data);
                break;
            default:
                break;
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public byte[] getRecordByteArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((ByteArray) records.get(recordIndex).getData(fieldIndex)).getArray();
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public short[] getRecordShortArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((ShortArray) records.get(recordIndex).getData(fieldIndex)).getArray();
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public int[] getRecordIntArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((IntegerArray) records.get(recordIndex).getData(fieldIndex)).getArray();

        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public long[] getRecordLongArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((LongArray) records.get(recordIndex).getData(fieldIndex)).getArray();

        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public float[] getRecordFloatArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((FloatArray) records.get(recordIndex).getData(fieldIndex)).getArray();
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public double[] getRecordDoubleArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((DoubleArray) records.get(recordIndex).getData(fieldIndex)).getArray();
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public String getRecordStringData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((StringArray) records.get(recordIndex).getData(fieldIndex)).get(0);
        }
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public String[] getRecordStringArrayData(int recordIndex, int fieldIndex) {
        if (records.get(recordIndex).getData(fieldIndex) == null) {
            return null;
        } else {
            return ((StringArray) records.get(recordIndex).getData(fieldIndex)).getArray();
        }
    }

    public Object getRecordObjectData(int recordIndex, int fieldIndex) {
        Object result = null;
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                result = getRecordByteData(recordIndex, fieldIndex);
                break;
            case Global.DATA_SHORT:
                result = getRecordShortData(recordIndex, fieldIndex);
                break;
            case Global.DATA_INT:
                result = getRecordIntData(recordIndex, fieldIndex);
                break;
            case Global.DATA_LONG:
                result = getRecordLongData(recordIndex, fieldIndex);
                break;
            case Global.DATA_FLOAT:
                result = getRecordFloatData(recordIndex, fieldIndex);
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                result = getRecordDoubleData(recordIndex, fieldIndex);
                break;
            case Global.DATA_STRING:
            case Global.DATA_MULTI_STRING:
            case Global.DATA_LIST:
            case Global.DATA_DATE:
            case Global.DATA_CUSTOM_COLOR:
            case Global.DATA_BITMAP:
            case Global.DATA_RESULT:
            case Global.DATA_VECTOR_SYMBOL:
            case Global.DATA_DEFINED_COLOR:
                result = getRecordStringData(recordIndex, fieldIndex);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 以字符串形式获得表格单元格数据
     *
     * @param recordIndex 行号
     * @param fieldIndex 列号
     * @param decimalNumber 浮点数小数位数,如果等于null，表示小数位数没有指定，全部输出
     * @return 格式化后的表格单元格数据
     */
    public String getRecordStringDataForcibly(int recordIndex, int fieldIndex, Integer decimalNumber) {
        String result = null;
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                result = String.valueOf(getRecordByteData(recordIndex, fieldIndex));
                break;
            case Global.DATA_SHORT:
                result = String.valueOf(getRecordShortData(recordIndex, fieldIndex));
                break;
            case Global.DATA_INT:
                result = String.valueOf(getRecordIntData(recordIndex, fieldIndex));
                break;
            case Global.DATA_LONG:
                result = String.valueOf(getRecordLongData(recordIndex, fieldIndex));
                break;
            case Global.DATA_FLOAT:
                float floatValue = getRecordFloatData(recordIndex, fieldIndex);
                if (decimalNumber != null) {
                    String format = "%10." + decimalNumber + "f";
                    result = String.format(format, floatValue);
                } else {
                    result = String.valueOf(floatValue);
                }
//                if (decimalNumber != null) {
//                    int decimal = decimalNumber.intValue();
//                    NumberFormat numberFormat = NumberFormat.getInstance();
//                    numberFormat.setMaximumFractionDigits(decimal);
//                    numberFormat.setMinimumFractionDigits(decimal);
//
//                    result = numberFormat.format(floatValue);
//                } else {
//                    result = String.valueOf(floatValue);
//                }
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                double doubleValue = getRecordDoubleData(recordIndex, fieldIndex);
                if (decimalNumber != null) {
                    String format = "%10." + decimalNumber + "f";
                    result = String.format(format, doubleValue);
                } else {
                    result = String.valueOf(doubleValue);
                }
//                if (decimalNumber != null) {
//                    int decimal = decimalNumber.intValue();
//                    NumberFormat numberFormat = NumberFormat.getInstance();
//                    numberFormat.setMaximumFractionDigits(decimal);
//                    numberFormat.setMinimumFractionDigits(decimal);
//
//                    result = numberFormat.format(doubleValue);
//                } else {
//                    result = String.valueOf(doubleValue);
//                }
                break;
            case Global.DATA_STRING:
            case Global.DATA_MULTI_STRING:
            case Global.DATA_LIST:
            case Global.DATA_DATE:
            case Global.DATA_CUSTOM_COLOR:
            case Global.DATA_BITMAP:
            case Global.DATA_RESULT:
            case Global.DATA_VECTOR_SYMBOL:
            case Global.DATA_DEFINED_COLOR:
                result = getRecordStringData(recordIndex, fieldIndex);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 以双精度浮点数形式获得表格单元格数据
     *
     * @param recordIndex 行号
     * @param fieldIndex 列号
     * @return 读取的双精度浮点数据
     */
    public float getRecordFloatDataForcibly(int recordIndex, int fieldIndex) {
        float result = Global.NULL_FLOAT_VALUE;
        switch (tableFields.getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                result = getRecordByteData(recordIndex, fieldIndex);
                break;
            case Global.DATA_SHORT:
                result = getRecordShortData(recordIndex, fieldIndex);
                break;
            case Global.DATA_INT:
                result = getRecordIntData(recordIndex, fieldIndex);
                break;
            case Global.DATA_LONG:
                result = getRecordLongData(recordIndex, fieldIndex);
                break;
            case Global.DATA_FLOAT:
                result = getRecordFloatData(recordIndex, fieldIndex);
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                result = (float) getRecordDoubleData(recordIndex, fieldIndex);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public byte getRecordByteData(int recordIndex, int fieldIndex) {
        ByteArray byteArray = ((ByteArray) records.get(recordIndex).getData(fieldIndex));
        if (byteArray == null || byteArray.getSize() == 0) {
            return Global.NULL_BYTE_VALUE;
        }
        return byteArray.get(0);
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public short getRecordShortData(int recordIndex, int fieldIndex) {
        ShortArray shortArray = ((ShortArray) records.get(recordIndex).getData(fieldIndex));
        if (shortArray == null || shortArray.getSize() == 0) {
            return Global.NULL_SHORT_VALUE;
        }
        return shortArray.get(0);
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public int getRecordIntData(int recordIndex, int fieldIndex) {
        IntegerArray integerArray = ((IntegerArray) records.get(recordIndex).getData(fieldIndex));
        if (integerArray == null || integerArray.getSize() == 0) {
            return Global.NULL_INT_VALUE;
        }
        return integerArray.get(0);
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public long getRecordLongData(int recordIndex, int fieldIndex) {
        LongArray longArray = ((LongArray) records.get(recordIndex).getData(fieldIndex));
        if (longArray == null || longArray.getSize() == 0) {
            return Global.NULL_LONG_VALUE;
        }
        return longArray.get(0);
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public float getRecordFloatData(int recordIndex, int fieldIndex) {
        NumberValueArray numberArray = ((NumberValueArray) records.get(recordIndex).getData(fieldIndex));
        if (numberArray == null || numberArray.getSize() == 0) {
            return Global.NULL_FLOAT_VALUE;
        }
        if (numberArray instanceof FloatArray) {
            FloatArray floatArray = (FloatArray) numberArray;
            return floatArray.get(0);
        }
        return numberArray.getValue(0).floatValue();
    }

    /**
     * 获取单元格数值
     *
     * @param recordIndex 行数
     * @param fieldIndex 列数
     * @return 单元格数值
     */
    public double getRecordDoubleData(int recordIndex, int fieldIndex) {
        NumberValueArray numberArray = ((NumberValueArray) records.get(recordIndex).getData(fieldIndex));
        if (numberArray == null || numberArray.getSize() == 0) {
            return Global.NULL_DOUBLE_VALUE;
        }
        if (numberArray instanceof DoubleArray) {
            DoubleArray doubleArray = (DoubleArray) numberArray;
            return doubleArray.get(0);
        }
        return numberArray.getValue(0).doubleValue();
    }

    /**
     * 获取单元格二维数组
     *
     * @return 单元格二维数组，类型为ValueArray类型
     */
    public ValueArray[][] getData() {
        ValueArray[][] recordsArray = new ValueArray[recordNum][tableFields.getFieldNum()];
        for (int i = 0; i < recordNum; i++) {
            for (int j = 0; j < tableFields.getFieldNum(); j++) {
                recordsArray[i][j] = records.get(i).getData(j);
            }
        }
        return recordsArray;
    }

    /**
     * 向表格末尾追加表格记录
     *
     * @param tableRecords 追加的表格记录
     */
    public void appendTableRecords(TableRecords tableRecords) {
        for (int i = 0; i < tableRecords.recordNum; i++) {
            OneRowRecords oneRowRecords = new OneRowRecords();
            oneRowRecords.setData(tableRecords.records.get(i).clone().getData());
            records.add(oneRowRecords);
        }
        recordNum = recordNum + tableRecords.recordNum;
    }

    /**
     * 删除表格记录
     *
     * @param index 待删除的记录行号
     * @return 删除操作是否成功
     */
    public int deleteTableRecords(int index) {
        if (index < 0 || index > records.size() - 1) {
            return Global.ERROR_FAILURE;
        }

        records.remove(index);
        recordNum = recordNum - 1;
        return Global.ERROR_SUCCESS;
    }

    /**
     * 删除表格记录
     *
     * @param index 待删除的记录行号
     * @param rowCount 删除的行数
     * @return 删除操作是否成功
     */
    public int deleteTableRecords(int index, int rowCount) {
        if (index < 0 || index > records.size() - 1 || index + rowCount > records.size()) {
            return Global.ERROR_FAILURE;
        }

        for (int i = index + rowCount - 1; i >= index; i--) {
            deleteTableRecords(i);
        }
        return Global.ERROR_SUCCESS;
    }

    /**
     * 将表格记录插入index行后
     *
     * @param index 插入位置行序号
     * @param tableRecords 插入的表格记录
     * @return 插入是否成功
     */
    public int insertTableRecords(int index, TableRecords tableRecords) {
        if (index < 0 || index > records.size()) {
            return Global.ERROR_FAILURE;
        }

        for (int i = 0; i < tableRecords.recordNum; i++) {
            OneRowRecords oneRowRecords = new OneRowRecords();
            oneRowRecords.setData(tableRecords.records.get(i).clone().getData());
            records.add(index + i, oneRowRecords);
        }
        recordNum = recordNum + tableRecords.recordNum;
        return Global.ERROR_SUCCESS;
    }

    public String[] getRecordString(int recordIndex, int fieldIndex) {
        ValueArray value = getRecordData(recordIndex, fieldIndex);
        return value.toStringArray();
    }

    public void setRecordString(int recordIndex, int fieldIndex, String[] data) {
        switch (getTableFields().getDataType(fieldIndex)) {
            case Global.DATA_BYTE:
                ByteArray byteArray = new ByteArray();
                byteArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, byteArray);
                break;
            case Global.DATA_INT:
                IntegerArray intArray = new IntegerArray();
                intArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, intArray);
                break;
            case Global.DATA_LONG:
                LongArray longArray = new LongArray();
                longArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, longArray);
                break;
            case Global.DATA_SHORT:
                ShortArray shortArray = new ShortArray();
                shortArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, shortArray);
                break;
            case Global.DATA_FLOAT:
                FloatArray floatArray = new FloatArray();
                floatArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, floatArray);
                break;
            case Global.DATA_DOUBLE:
            case Global.DATA_DEPTH:
                DoubleArray doubleArray = new DoubleArray();
                doubleArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, doubleArray);
                break;
            default:
                StringArray stringArray = new StringArray();
                stringArray.parseStringArray(data);
                setRecordData(recordIndex, fieldIndex, stringArray);
        }
    }

    public ValueArray getRecordData(int recordIndex, int fieldIndex) {
        return records.get(recordIndex).getData(fieldIndex);
    }

    public void setRecordData(int recordIndex, int fieldIndex, ValueArray val) {
        records.get(recordIndex).setData(fieldIndex, val);
    }

    /* add these functions by wangcaizhi 2017.2.6  */
    public void removeField(int fieldIndex) {
        for (int i = 0; i < recordNum; i++) {
            OneRowRecords aRow = records.get(i);
            aRow.remove(fieldIndex);
        }
    }

    public void addNullField(int fieldIndex) {
        for (int i = 0; i < recordNum; i++) {
            OneRowRecords aRow = records.get(i);
            aRow.add(fieldIndex, null);
        }
    }

    public void addNullRecord(int rowIndex) {
        records.add(rowIndex, new OneRowRecords(tableFields.getFieldNum()));
        recordNum++;
    }

    public int addNullRecord() {
        records.add(new OneRowRecords(tableFields.getFieldNum()));
        recordNum++;
        return recordNum - 1;
    }

    /**
     * 如果表格的第一列为深度列， 允许对表格按深度进行排序
     */
    public void sortByDepth() {
        Collections.sort(records, comparator);
    }
    private Comparator<OneRowRecords> comparator = new Comparator<OneRowRecords>() {
        public int compare(OneRowRecords r1, OneRowRecords r2) {
            try {
                return Double.compare(
                        ((NumberValueArray) r1.records.get(0)).getValue(0).doubleValue(),
                        ((NumberValueArray) r2.records.get(0)).getValue(0).doubleValue());
            } catch (Exception e) {
                return 0;
            }
        }
    };

    /**
     * 内部类，为表格记录的一行
     */
    public class OneRowRecords implements Cloneable {

        private ArrayList<ValueArray> records = new ArrayList<ValueArray>();

        public OneRowRecords() {

        }

        public ArrayList<ValueArray> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<ValueArray> records) {
            this.records = records;
        }

        public OneRowRecords(int fieldNum) {
            for (int i = 0; i < fieldNum; i++) {
                records.add(null);
            }
        }

        /**
         * 设置该行index个位置的值
         *
         * @param index 列序号
         * @param valueArray 所设置的值
         */
        public void setData(int index, ValueArray valueArray) {
            records.remove(index);
            records.add(index, valueArray);
        }

        public void addData(ValueArray valueArray) {
            records.add(valueArray);
        }

        public void add(int index, ValueArray valueArray) {
            records.add(index, valueArray);
        }

        public void remove(int index) {
            records.remove(index);
        }

        /**
         * 设置整行数据
         *
         */
        public void setData(ArrayList<ValueArray> aRowData) {
            records = aRowData;
        }

        /**
         * 获取第index个记录的数据
         *
         * @param index 列序号
         * @return index位置的数据
         */
        public ValueArray getData(int index) {
            return records.get(index);
        }

        /**
         * 获取整行数据值
         *
         * @return 整行数据，类型为ValueArray[]类型
         */
        public ArrayList<ValueArray> getData() {
            return records;
        }

        @Override
        public OneRowRecords clone() {
            OneRowRecords newRow = new OneRowRecords();
            for (int i = 0; i < records.size(); i++) {
                newRow.addData(records.get(i) == null ? null : records.get(i).clone());
            }
            return newRow;
        }
    }

    @Override
    public String toString() {
        return "TableRecords{" +
                "\"records\":" + records +
                ", \"tableFields\":" + tableFields +
                ", \"recordNum\":" + recordNum +
                ", \"comparator\":" + comparator +
                '}';
    }
}
