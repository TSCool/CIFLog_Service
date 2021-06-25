package com.cif.utils.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.baseutil.array.*;

import java.util.ArrayList;
import java.util.List;

public class DataTypeSwitch {

    /**
     * 字符串转为Int
     * @param data
     * @return
     */
    public static int toInt(String data){
        return data.equals("") || data == null ? null : Integer.parseInt(data);
    }

    /**
     * 根据json数据获取ByteArray对象
     * @return
     */
    public static ByteArray getByteArray(JSONObject object2){
        ByteArray byteArr = new ByteArray();
        JSONArray byteArrayValue = object2.getJSONArray("array");
        byte[] arrayByte = new byte[byteArrayValue.size()];
        for(int g=0;g<byteArrayValue.size();g++){
            String str = byteArrayValue.getString(g);
            arrayByte = str.getBytes();
        }
        int byteSize = (int)object2.get("size");
        int byteCapacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
        byteArr.set_size(byteSize);
        byteArr.setCapacity(byteCapacity);
        byteArr.setIncreaseSize(10);
        byteArr.setArray(arrayByte);
        return byteArr;
    }

    /**
     * 根据json数据获取IntegerArray对象
     * @return
     */
    public static IntegerArray getIntegerArray(JSONObject object2){
        IntegerArray inteArr = new IntegerArray();
        JSONArray inteArrayValue = object2.getJSONArray("array");
        int[] arrayInte = new int[inteArrayValue.size()];
        for(int g=0;g<inteArrayValue.size();g++){
            arrayInte[g] = inteArrayValue.getIntValue(g);
        }
        int douSize = (int)object2.get("size");
        int douCapacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
        inteArr.set_size(douSize);
        inteArr.setCapacity(douCapacity);
        inteArr.setIncreaseSize(10);
        inteArr.setArray(arrayInte);
        return inteArr;
    }

    /**
     * 根据json数据获取LongArray对象
     * @return
     */
    public static LongArray getLongArray(JSONObject object2){
        LongArray longArr = new LongArray();
        if(object2 == null){
            long[] arrayLong = new long[0];
            longArr.set_size(0);
            longArr.setCapacity(0);
            longArr.setIncreaseSize(10);
            longArr.setArray(arrayLong);
        }else{
            JSONArray longArrayValue = object2.getJSONArray("array");
            long[] arrayLong = new long[longArrayValue.size()];
            for(int g=0;g<longArrayValue.size();g++){
                arrayLong[g] = longArrayValue.getLong(g);
            }
            int longSize = (int)object2.get("size");
            int longCapacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
            longArr.set_size(longSize);
            longArr.setCapacity(longCapacity);
            longArr.setIncreaseSize(10);
            longArr.setArray(arrayLong);
        }
        return longArr;
    }

    /**
     * 根据json数据获取FloadArray对象
     * @return
     */
    public static FloatArray getFloatArray(JSONObject object2){
        FloatArray floatArr = new FloatArray();
        JSONArray floatArrayValue = object2.getJSONArray("array");
        float[] arrayFloat = new float[floatArrayValue.size()];
        for(int g=0;g<floatArrayValue.size();g++){
            arrayFloat[g] = floatArrayValue.getFloat(g);
        }
        int longSize = (int)object2.get("size");
        int longCapacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
        floatArr.set_size(longSize);
        floatArr.setCapacity(longCapacity);
        floatArr.setIncreaseSize(10);
        floatArr.setArray(arrayFloat);
        return floatArr;
    }

    /**
     * 根据json数据获取DoubleArray对象
     * @return
     */
    public static DoubleArray getDoubleArray(JSONObject object2){
        DoubleArray douArr = new DoubleArray();
        JSONArray douArrayValue = object2.getJSONArray("array");
        double[] arrayDou = new double[douArrayValue.size()];
        for(int g=0;g<douArrayValue.size();g++){
            arrayDou[g] = douArrayValue.getDouble(g);
        }
        int inteSize = (int)object2.get("size");
        int inteCapacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
        douArr.set_size(inteSize);
        douArr.setCapacity(inteCapacity);
        douArr.setIncreaseSize(10);
        douArr.setArray(arrayDou);
        return douArr;
    }

    /**
     * 根据json数据获取StringArray对象
     * @return
     */
    public static StringArray getStringArray(JSONObject object2){
        StringArray strArr = new StringArray();
        JSONArray arrayValue = object2.getJSONArray("array");
        String[] arrayStr = new String[arrayValue.size()];
        for(int k=0;k<arrayValue.size();k++){
            arrayStr[k] = arrayValue.get(k).toString();
        }
        int size = (int)object2.get("size");
        int capacity = (int)object2.get("capacity");
//                    int increaseSize = (int)object2.get("increaseSize");
        strArr.set_size(size);
        strArr.setCapacity(capacity);
        strArr.setIncreaseSize(10);
        strArr.setArray(arrayStr);
        return strArr;
    }
}
