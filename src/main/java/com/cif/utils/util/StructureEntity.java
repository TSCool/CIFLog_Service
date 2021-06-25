package com.cif.utils.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.baseutil.array.*;
import com.cif.utils.constant.Global;
import com.cif.utils.dataengine.TableFields;
import com.cif.utils.dataengine.TableRecords;

import java.util.ArrayList;

public class StructureEntity {

    /**
     * 构建TableFields实体类
     * @return
     */
    public static TableFields structureTableFields(JSONObject tableObj){
        //通过获取到的tableFields各属性的值，进行赋值
        TableFields tableFields = new TableFields();
        int fieldNum = (int)tableObj.get("fieldNum");
        tableFields.init(fieldNum);

        tableFields.setTemplateName(tableFields.getTemplateName());


        JSONArray fieldNamesArray = tableObj.getJSONArray("fieldNames");

        JSONArray fieldUnitsArray = tableObj.getJSONArray("fieldUnits");

        JSONArray dataTypesArray = tableObj.getJSONArray("dataTypes");

        JSONArray enumStringsArray = tableObj.getJSONArray("enumStrings");

        JSONArray dataNumsArray = tableObj.getJSONArray("dataNums");

        for(int i=0;i<tableFields.getFieldNum();i++){
            tableFields.setName(i, fieldNamesArray.get(i) == null ? " " : fieldNamesArray.get(i).toString());
            tableFields.setDataNum(i, dataNumsArray.getInteger(i));
            tableFields.setDataType(i, dataTypesArray.getByte(i));
            tableFields.setEnumString(i, enumStringsArray.get(i) == null ? " " : enumStringsArray.get(i).toString());
            tableFields.setUnit(i, fieldUnitsArray.get(i) == null ? " " : fieldUnitsArray.get(i).toString());
        }

        JSONArray defaultValuesArray = tableObj.getJSONArray("defaultValues");
        ArrayList<ValueArray> defaultValues = new ArrayList<ValueArray>();
        byte dataType;
        for(int i= 0;i<defaultValuesArray.size();i++){
            dataType = tableFields.getDataType(i);
            if(defaultValuesArray.get(i) != null){
                JSONObject object2 = (JSONObject) defaultValuesArray.get(i);

                switch (dataType) {
                    case Global.DATA_BYTE:
                        ByteArray byteArr = DataTypeSwitch.getByteArray(object2);
                        defaultValues.add(byteArr);
                        break;
                    case Global.DATA_SHORT:

                        break;
                    case Global.DATA_INT:
                        IntegerArray inteArr = DataTypeSwitch.getIntegerArray(object2);
                        defaultValues.add(inteArr);
                        break;
                    case Global.DATA_LONG:
                        LongArray longArr = DataTypeSwitch.getLongArray(object2);
                        defaultValues.add(longArr);
                        break;
                    case Global.DATA_FLOAT:
                        FloatArray floatArr = DataTypeSwitch.getFloatArray(object2);
                        defaultValues.add(floatArr);
                        break;
                    case Global.DATA_DOUBLE:
                    case Global.DATA_DEPTH:
                        DoubleArray douArr = DataTypeSwitch.getDoubleArray(object2);
                        defaultValues.add(douArr);
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
                        StringArray strArr = DataTypeSwitch.getStringArray(object2);
                        defaultValues.add(strArr);
                        break;
                }
            }else{
                defaultValues.add(null);
            }
        }

        for(int i=0;i<tableFields.getFieldNum();i++){
            tableFields.setDefaultValue(i, defaultValues.get(i));
        }

        return tableFields;
    }

    /**
     * 构建records集合
     * @param array
     * @return
     */
    public static ArrayList<TableRecords.OneRowRecords> structureRecords(JSONArray array, TableFields tableFields){
        ArrayList<TableRecords.OneRowRecords> records = new ArrayList<TableRecords.OneRowRecords>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            //子集
            JSONArray array2 = object.getJSONArray("data");
            TableRecords rec = new TableRecords();
            TableRecords.OneRowRecords oneRowRecords = rec.new OneRowRecords();
            ArrayList<ValueArray> value = new ArrayList<ValueArray>();
            for (int j = 0; j < array2.size(); j++) {
                JSONObject object2 = (JSONObject) array2.get(j);

                if(object2 != null){
                    ValueArray test = null;
                    switch (tableFields.getDataType(j)) {
                        case Global.DATA_BYTE:
                            ByteArray byteArr = DataTypeSwitch.getByteArray(object2);
                            value.add(byteArr);
                            break;
                        case Global.DATA_SHORT:

                            break;
                        case Global.DATA_INT:
                            IntegerArray inteArr = DataTypeSwitch.getIntegerArray(object2);
                            value.add(inteArr);
                            break;
                        case Global.DATA_LONG:
                            LongArray longArr = DataTypeSwitch.getLongArray(object2);
                            value.add(longArr);
                            break;
                        case Global.DATA_FLOAT:
                            FloatArray floatArr = DataTypeSwitch.getFloatArray(object2);
                            value.add(floatArr);
                            break;
                        case Global.DATA_DOUBLE:
                        case Global.DATA_DEPTH:
                            DoubleArray douArr = DataTypeSwitch.getDoubleArray(object2);
                            value.add(douArr);
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
                            StringArray strArr = DataTypeSwitch.getStringArray(object2);
                            value.add(strArr);
                            break;
                    }
                }else{
                    value.add(null);
                }
            }
            oneRowRecords.setRecords(value);
            records.add(oneRowRecords);
        }
        return records;
    }

}
