/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.wrapper;

import com.cif.utils.constant.Global;
import com.cif.utils.PathUtil;
import com.cif.utils.baseutil.CsvReader;
import com.cif.utils.baseutil.CsvWriter;
import com.cif.utils.baseutil.array.*;
import com.cif.utils.dataengine.TableFields;
import com.cif.utils.dataengine.TableRecords;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xsj
 */
public class TableRwWrapper {

    private String filePath = null;
    private CsvReader csvReader = null;
    private CsvWriter csvWriter = null;
    static final String VALUE_DELIMITER = "@";

    public TableRwWrapper(String filePath) {
        this.filePath = filePath;
        //创建目录
        File dir = new File(PathUtil.getFullPathNoEndSeparator(filePath));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void close() {
        if (csvReader != null) {
            csvReader.close();
            csvReader = null;
        }
        if (csvWriter != null) {
            csvWriter.close();
            csvWriter = null;
        }
    }

    public int readTableFields(TableFields tableFields) {
        return readTableFieldsFromFile(tableFields);
    }

    public int readTableRecords(int indexToRead, int readingNumber, TableRecords tableRecords) {
        TableRecords records = readTableRecords();
        if (records == null) {
            return Global.ERROR_FAILURE;
        }
        records.readTableRecords(indexToRead, readingNumber, tableRecords);
        return Global.ERROR_SUCCESS;
    }

    public int readTableRecords(TableRecords tableRecords) {
        TableRecords records = readTableRecords();
        if (records == null) {
            return Global.ERROR_FAILURE;
        } else {
            records.readTableRecords(0, records.getRecordsNum(), tableRecords);
            return Global.ERROR_SUCCESS;
        }
    }

    private int readTableFieldsFromFile(TableFields tableFields) {
        try {
            if (csvReader == null) {
                csvReader = new CsvReader(filePath, ',', Charset.forName("GBK"));
            }

            //第一行：表格模板名称
            csvReader.readRecord();
            int columnCount = csvReader.getColumnCount();
            tableFields.init(columnCount);
            for (int i = 0; i < columnCount; i++) {
                tableFields.setName(i, csvReader.get(i));
            }
            //第二行：表格模板单位
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setUnit(i, csvReader.get(i));
            }
            //第三行：表格模板数据类型
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setDataType(i, Byte.parseByte(csvReader.get(i)));
            }
            //第四行：表格模板列表值
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setEnumString(i, csvReader.get(i));
            }
            //第五行：表格模板默认值
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                String cellValue = csvReader.get(i);
                if (cellValue == null || cellValue.isEmpty()) {
                    continue;
                }

                String[] split = csvReader.get(i).split(VALUE_DELIMITER);
                if (split != null && split.length > 0) {
                    switch (tableFields.getDataType(i)) {
                        case Global.DATA_BYTE:
                            ByteArray byteValue = new ByteArray();
                            for (String split1 : split) {
                                byteValue.add(Byte.parseByte(split1));
                            }
                            tableFields.setDefaultValue(i, byteValue);
                            break;
                        case Global.DATA_INT:
                            IntegerArray intValue = new IntegerArray();
                            for (String split1 : split) {
                                intValue.add(Integer.parseInt(split1));
                            }
                            tableFields.setDefaultValue(i, intValue);
                            break;
                        case Global.DATA_SHORT:
                            ShortArray shortValue = new ShortArray();
                            for (String split1 : split) {
                                shortValue.add(Short.parseShort(split1));
                            }
                            tableFields.setDefaultValue(i, shortValue);
                            break;
                        case Global.DATA_LONG:
                            LongArray longValue = new LongArray();
                            for (String split1 : split) {
                                longValue.add(Long.parseLong(split1));
                            }
                            tableFields.setDefaultValue(i, longValue);
                            break;
                        case Global.DATA_FLOAT:
                            FloatArray floatValue = new FloatArray();
                            for (String split1 : split) {
                                floatValue.add(Float.parseFloat(split1));
                            }
                            tableFields.setDefaultValue(i, floatValue);
                            break;
                        case Global.DATA_DOUBLE:
                        case Global.DATA_DEPTH:
                            DoubleArray doubleValue = new DoubleArray();
                            for (String split1 : split) {
                                doubleValue.add(Double.parseDouble(split1));
                            }
                            tableFields.setDefaultValue(i, doubleValue);
                            break;
                        case Global.DATA_STRING:
                        case Global.DATA_MULTI_STRING:
                        case Global.DATA_LIST:
                        case Global.DATA_DATE:
                        case Global.DATA_BITMAP:
                        case Global.DATA_CUSTOM_COLOR:
                        case Global.DATA_RESULT:
                        case Global.DATA_VECTOR_SYMBOL:
                        case Global.DATA_DEFINED_COLOR:
                            StringArray stringValue = new StringArray();
                            for (String split1 : split) {
                                stringValue.add(split1);
                            }
                            tableFields.setDefaultValue(i, stringValue);
                    }
                }
            }
            //第六行：表格模板默认长度
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setDataNum(i, Integer.parseInt(csvReader.get(i)));
            }
            csvReader.close();
            close();
            return Global.ERROR_SUCCESS;
        } catch (IOException ex) {
            Logger.getLogger(TableRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return Global.ERROR_FAILURE;
        }
    }

    private TableRecords readTableRecords() {
        try {
            if (csvReader == null) {
                csvReader = new CsvReader(filePath, ',', Charset.forName("GBK"));
            }

            TableFields tableFields = new TableFields();
            //第一行：表格模板名称
            csvReader.readRecord();
            int columnCount = csvReader.getColumnCount();
            tableFields.init(columnCount);
            for (int i = 0; i < columnCount; i++) {
                tableFields.setName(i, csvReader.get(i));
            }
            //第二行：表格模板单位
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setUnit(i, csvReader.get(i));
            }
            //第三行：表格模板数据类型
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setDataType(i, Byte.parseByte(csvReader.get(i)));
            }
            //第四行：表格模板列表值
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setEnumString(i, csvReader.get(i));
            }
            //第五行：表格模板默认值
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                String cellValue = csvReader.get(i);
                if (cellValue == null || cellValue.isEmpty()) {
                    continue;
                }

                String[] split = csvReader.get(i).split(VALUE_DELIMITER);
                if (split != null && split.length > 0) {
                    switch (tableFields.getDataType(i)) {
                        case Global.DATA_BYTE:
                            ByteArray byteValue = new ByteArray();
                            for (String split1 : split) {
                                byteValue.add(Byte.parseByte(split1));
                            }
                            tableFields.setDefaultValue(i, byteValue);
                            break;
                        case Global.DATA_INT:
                            IntegerArray intValue = new IntegerArray();
                            for (String split1 : split) {
                                intValue.add(Integer.parseInt(split1));
                            }
                            tableFields.setDefaultValue(i, intValue);
                            break;
                        case Global.DATA_SHORT:
                            ShortArray shortValue = new ShortArray();
                            for (String split1 : split) {
                                shortValue.add(Short.parseShort(split1));
                            }
                            tableFields.setDefaultValue(i, shortValue);
                            break;
                        case Global.DATA_LONG:
                            LongArray longValue = new LongArray();
                            for (String split1 : split) {
                                longValue.add(Long.parseLong(split1));
                            }
                            tableFields.setDefaultValue(i, longValue);
                            break;
                        case Global.DATA_FLOAT:
                            FloatArray floatValue = new FloatArray();
                            for (String split1 : split) {
                                floatValue.add(Float.parseFloat(split1));
                            }
                            tableFields.setDefaultValue(i, floatValue);
                            break;
                        case Global.DATA_DOUBLE:
                        case Global.DATA_DEPTH:
                            DoubleArray doubleValue = new DoubleArray();
                            for (String split1 : split) {
                                doubleValue.add(Double.parseDouble(split1));
                            }
                            tableFields.setDefaultValue(i, doubleValue);
                            break;
                        case Global.DATA_STRING:
                        case Global.DATA_MULTI_STRING:
                        case Global.DATA_LIST:
                        case Global.DATA_DATE:
                        case Global.DATA_BITMAP:
                        case Global.DATA_CUSTOM_COLOR:
                        case Global.DATA_RESULT:
                        case Global.DATA_VECTOR_SYMBOL:
                        case Global.DATA_DEFINED_COLOR:
                            StringArray stringValue = new StringArray();
                            for (String split1 : split) {
                                stringValue.add(split1);
                            }
                            tableFields.setDefaultValue(i, stringValue);
                    }
                }
            }
            //第六行：表格模板默认长度
            csvReader.readRecord();
            for (int i = 0; i < columnCount; i++) {
                tableFields.setDataNum(i, Integer.parseInt(csvReader.get(i)));
            }
            //从第七行开始为表格数据
            TableRecords tableRecords = new TableRecords(tableFields);
            while (csvReader.readRecord()) {
                TableRecords rowRecords = new TableRecords(1, tableFields);
                for (int i = 0; i < columnCount; i++) {
                    String cellValue = csvReader.get(i);
                    if (cellValue == null || cellValue.isEmpty()) {
                        continue;
                    }

                    String[] split = cellValue.split(VALUE_DELIMITER);
                    if (split != null && split.length > 0) {
                        switch (tableFields.getDataType(i)) {
                            case Global.DATA_BYTE:
                                ByteArray byteData = new ByteArray();
                                for (String split1 : split) {
                                    byteData.add(Byte.parseByte(split1));
                                }
                                rowRecords.setRecordByteData(0, i, byteData.getArray());
                                break;
                            case Global.DATA_INT:
                                IntegerArray intData = new IntegerArray();
                                for (String split1 : split) {
                                    intData.add(Integer.parseInt(split1));
                                }
                                rowRecords.setRecordIntData(0, i, intData.getArray());
                                break;
                            case Global.DATA_LONG:
                                LongArray longData = new LongArray();
                                for (String split1 : split) {
                                    longData.add(Long.parseLong(split1));
                                }
                                rowRecords.setRecordLongData(0, i, longData.getArray());
                                break;
                            case Global.DATA_SHORT:
                                ShortArray shortData = new ShortArray();
                                for (String split1 : split) {
                                    shortData.add(Short.parseShort(split1));
                                }
                                rowRecords.setRecordShortData(0, i, shortData.getArray());
                                break;
                            case Global.DATA_FLOAT:
                                FloatArray floatData = new FloatArray();
                                for (String split1 : split) {
                                    floatData.add(Float.parseFloat(split1));
                                }
                                rowRecords.setRecordFloatData(0, i, floatData.getArray());
                                break;
                            case Global.DATA_DOUBLE:
                            case Global.DATA_DEPTH:
                                DoubleArray doubleData = new DoubleArray();
                                for (String split1 : split) {
                                    doubleData.add(Double.parseDouble(split1));
                                }
                                rowRecords.setRecordDoubleData(0, i, doubleData.getArray());
                                break;
                            case Global.DATA_STRING:
                            case Global.DATA_MULTI_STRING:
                            case Global.DATA_LIST:
                            case Global.DATA_DATE:
                            case Global.DATA_BITMAP:
                            case Global.DATA_CUSTOM_COLOR:
                            case Global.DATA_RESULT:
                            case Global.DATA_VECTOR_SYMBOL:
                            case Global.DATA_DEFINED_COLOR:
                                rowRecords.setRecordStringData(0, i, csvReader.get(i));
                        }
                    }
                }
                tableRecords.appendTableRecords(rowRecords);
            }
            close();
            return tableRecords;
        } catch (IOException ex) {
            Logger.getLogger(TableRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return null;
        }
    }

    public void writeTableRecords(TableRecords tableRecords) {
        try {
            if (csvWriter == null) {
                csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));
            }

            //第一行：表格模板名称
            TableFields tableFields = tableRecords.getTableFields();
            int columnCount = tableFields.getFieldNum();
            String[] values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = tableFields.getName(i);
            }
            csvWriter.writeRecord(values);
            //第二行：表格模板单位
            values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = tableFields.getUnit(i);
            }
            csvWriter.writeRecord(values);
            //第三行：表格模板数据类型
            values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = Byte.toString(tableFields.getDataType(i));
            }
            csvWriter.writeRecord(values);
            //第四行：表格模板列表值
            values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = tableFields.getEnumString(i);
            }
            csvWriter.writeRecord(values);
            //第五行：表格模板默认值
            values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = "";
                ValueArray defaultValueArray = tableFields.getDefaultValue(i);
                if (defaultValueArray == null || defaultValueArray.getSize() == 0) {
                    continue;
                }
                int size = defaultValueArray.getSize();
                switch (tableFields.getDataType(i)) {
                    case Global.DATA_BYTE:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Byte.toString(((ByteArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_INT:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Integer.toString(((IntegerArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_SHORT:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Short.toString(((ShortArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_LONG:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Long.toString(((LongArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_FLOAT:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Float.toString(((FloatArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_DOUBLE:
                    case Global.DATA_DEPTH:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += Double.toString(((DoubleArray) defaultValueArray).get(j));
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                        break;
                    case Global.DATA_STRING:
                    case Global.DATA_MULTI_STRING:
                    case Global.DATA_LIST:
                    case Global.DATA_DATE:
                    case Global.DATA_BITMAP:
                    case Global.DATA_CUSTOM_COLOR:
                    case Global.DATA_RESULT:
                    case Global.DATA_VECTOR_SYMBOL:
                    case Global.DATA_DEFINED_COLOR:
                        if (size > 0) {
                            for (int j = 0; j < size; j++) {
                                values[i] += ((StringArray) defaultValueArray).get(j);
                                if (j != size - 1) {
                                    values[i] += VALUE_DELIMITER;
                                }
                            }
                        }
                }
            }
            csvWriter.writeRecord(values);
            //第六行：表格模板默认长度
            values = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                values[i] = Integer.toString(tableFields.getDataNum(i));
            }
            csvWriter.writeRecord(values);

            //从第七行开始为表格数据
            int rowCount = tableRecords.getRecordsNum();
            for (int i = 0; i < rowCount; i++) {
                values = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    values[j] = "";
                    switch (tableFields.getDataType(j)) {
                        case Global.DATA_BYTE:
                            byte[] byteBuffer = tableRecords.getRecordByteArrayData(i, j);
                            if (byteBuffer == null || byteBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < byteBuffer.length; z++) {
                                values[j] += Byte.toString(byteBuffer[z]);
                                if (z != byteBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_SHORT:
                            short[] shortBuffer = tableRecords.getRecordShortArrayData(i, j);
                            if (shortBuffer == null || shortBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < shortBuffer.length; z++) {
                                values[j] += Short.toString(shortBuffer[z]);
                                if (z != shortBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_INT:
                            int[] intBuffer = tableRecords.getRecordIntArrayData(i, j);
                            if (intBuffer == null || intBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < intBuffer.length; z++) {
                                values[j] += Integer.toString(intBuffer[z]);
                                if (z != intBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_LONG:
                            long[] longBuffer = tableRecords.getRecordLongArrayData(i, j);
                            if (longBuffer == null || longBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < longBuffer.length; z++) {
                                values[j] += Long.toString(longBuffer[z]);
                                if (z != longBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_FLOAT:
                            float[] floatBuffer = tableRecords.getRecordFloatArrayData(i, j);
                            if (floatBuffer == null || floatBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < floatBuffer.length; z++) {
                                values[j] += Float.toString(floatBuffer[z]);
                                if (z != floatBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_DOUBLE:
                        case Global.DATA_DEPTH:
                            double[] doubleBuffer = tableRecords.getRecordDoubleArrayData(i, j);
                            if (doubleBuffer == null || doubleBuffer.length == 0) {
                                continue;
                            }
                            for (int z = 0; z < doubleBuffer.length; z++) {
                                values[j] += Double.toString(doubleBuffer[z]);
                                if (z != doubleBuffer.length - 1) {
                                    values[j] += VALUE_DELIMITER;
                                }
                            }
                            break;
                        case Global.DATA_STRING:
                        case Global.DATA_MULTI_STRING:
                        case Global.DATA_LIST:
                        case Global.DATA_DATE:
                        case Global.DATA_BITMAP:
                        case Global.DATA_CUSTOM_COLOR:
                        case Global.DATA_RESULT:
                        case Global.DATA_VECTOR_SYMBOL:
                        case Global.DATA_DEFINED_COLOR:
                            values[j] = tableRecords.getRecordStringData(i, j);
                    }
                }
                csvWriter.writeRecord(values);
            }
            csvWriter.flush();
            csvWriter.close();
            close();
        } catch (IOException ex) {
            Logger.getLogger(TableRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }
}
