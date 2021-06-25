/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.baseutil.array;

import com.cif.utils.constant.Global;

import java.util.Arrays;

/**
 * @author Administrator
 */
public class GeneralDataArrayFunction {
    public final static byte NORMALIEZ_TO_ONE = 1;
    public final static byte NORMALIEZ_TO_TEN = 10;
    public final static byte NORMALIEZ_TO_HUNDRED = 100;
    //对数组进行标准化  x(i) = [x(i)-x(min)]/[x(max)-x(min)]*10
    public static double[] getNormalizedArray(double[] dataArray, byte normalizeType){
        double[] newArray = new double[dataArray.length];
        double[] minMaxArray = GeneralDataArrayFunction.getMaxMinValueOfArray(dataArray);
        float factor = 1;
        switch(normalizeType){
            case NORMALIEZ_TO_ONE:
                factor = 1;
                break;
            case NORMALIEZ_TO_TEN:
                factor = 10;
                break;
            case NORMALIEZ_TO_HUNDRED:
                factor = 100;
                break;
        }
        for (int i = 0; i < newArray.length; i++) {
            if(dataArray[i] == Global.NULL_DOUBLE_VALUE || dataArray[i] == -9999){
                newArray[i] = 0;
                continue;
            }
            if(dataArray[i] == Double.NEGATIVE_INFINITY || Double.isInfinite(dataArray[i]) || Double.isNaN(dataArray[i])){
                newArray[i] = 0;
                continue;
            }
            if(minMaxArray[1] == minMaxArray [0])
                newArray[i] = factor;
            else 
                newArray[i] = (dataArray[i] - minMaxArray[0]) / (minMaxArray[1] - minMaxArray [0]) * factor;
        }
        return newArray;
    }
    
    //对数组进行标准化  x(i) = [x(i)-x(min)]/[x(max)-x(min)]*10
    public static double[] getNormalizedArray(double[] dataArray, byte normalizeType, double[] invalidValues){
        double[] newArray = new double[dataArray.length];
        double[] minMaxArray = GeneralDataArrayFunction.getMaxMinValueOfArray(dataArray, invalidValues);
        float factor = 1;
        switch(normalizeType){
            case NORMALIEZ_TO_ONE:
                factor = 1;
                break;
            case NORMALIEZ_TO_TEN:
                factor = 10;
                break;
            case NORMALIEZ_TO_HUNDRED:
                factor = 100;
                break;
        }
        for (int i = 0; i < newArray.length; i++) {
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS ){
                newArray[i] = dataArray[i];
                continue;
            }
            boolean isInvalid = false;
            for (int j = 0; j < invalidValues.length; j++) {
                if( Math.abs(dataArray[i] - invalidValues[j]) < Global.EPS ){
                    isInvalid = true;
                    break;
                }
            }
            if(isInvalid){
                newArray[i] = dataArray[i];
                continue;
            }
            
            if(dataArray[i] == Double.NEGATIVE_INFINITY || Double.isInfinite(dataArray[i]) || Double.isNaN(dataArray[i])){
                newArray[i] = dataArray[i];
                continue;
            }
            newArray[i] = (dataArray[i] - minMaxArray[0]) / (minMaxArray[1] - minMaxArray [0]) * factor;
        }
        return newArray;
    }
    
    /**
     * 对数组进行归一化
     * @param dataArray
     * @param normalizeType
     * @param minValue  最小值。数组中小于此值的，统统写为0；
     * @param maxValue  最大值。数组中大于此值的，统统按1（或10、100）处理
     * @return 
     */
    public static double[] getNormalizedArray(double[] dataArray, byte normalizeType, double minValue, double maxValue){
        double[] newArray = new double[dataArray.length];
        float factor = 1;
        switch(normalizeType){
            case NORMALIEZ_TO_ONE:
                factor = 1;
                break;
            case NORMALIEZ_TO_TEN:
                factor = 10;
                break;
            case NORMALIEZ_TO_HUNDRED:
                factor = 100;
                break;
        }
        for (int i = 0; i < newArray.length; i++) {
            if(dataArray[i] == Global.NULL_DOUBLE_VALUE || dataArray[i] == -9999){
                newArray[i] = 0;
                continue;
            }
            if(dataArray[i] == Double.NEGATIVE_INFINITY || Double.isInfinite(dataArray[i]) || Double.isNaN(dataArray[i])){
                newArray[i] = 0;
                continue;
            }
            if(dataArray[i] < minValue){
                newArray[i] = 0;
                continue;
            }
            if(dataArray[i] > maxValue){
                newArray[i] = factor;
                continue;
            }
            newArray[i] = (dataArray[i] - minValue) / (maxValue - minValue) * factor;
        }
        return newArray;
    }
    
    
    /**
     * 获取数组中元素的最小值和最大值. 
     * @param dataArray  参数数组中的无效值被忽略
     * @return 返回一个包含两元素的数组，第一个元素是最小值，第二个元素是最大值
     */
    public static double[] getMaxMinValueOfArray(double[] dataArray){
        double[] minMax = new double[2];
        double min = 0, max = Global.NULL_DOUBLE_VALUE;
        for (int i = 0; i < dataArray.length; i++) {
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            
            if(dataArray[i] == Double.NEGATIVE_INFINITY || dataArray[i] == Double.POSITIVE_INFINITY || Double.isNaN(dataArray[i]))
                continue;
            
            min = dataArray[i];
            max = dataArray[i];
            break;
        }
        
        for (int i = 0; i < dataArray.length; i++) {
            if(dataArray[i] == Double.NEGATIVE_INFINITY || dataArray[i] == Double.POSITIVE_INFINITY || Double.isNaN(dataArray[i]))
                continue;
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            if( min > dataArray[i])
                min = dataArray[i];
            if(max < dataArray[i])
                max = dataArray[i];
        }
        minMax[0] = min;
        minMax[1] = max;
        return minMax;
    }
    
    /**
     * 获取数组中元素的最小值和最大值. 
     * @param dataArray  
     * @param invalidValues   无效值数组；元素取值等于该数值时需要忽略该元素
     * @return 返回一个包含两元素的数组，第一个元素是最小值，第二个元素是最大值
     */
    public static double[] getMaxMinValueOfArray(double[] dataArray, double[] invalidValues){
        double[] minMax = new double[2];
        double min = 99999, max = Global.NULL_DOUBLE_VALUE;
        for (int i = 0; i < dataArray.length; i++) {
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            
            if(invalidValues != null){
                boolean isInvalid = false;
                for (int j = 0; j < invalidValues.length; j++) {
                    if ( Math.abs(dataArray[i] - invalidValues[j]) < Global.EPS ){
                        isInvalid = true;
                        break;
                    }
                }
                if(isInvalid)
                    continue;
            }
            
            if(dataArray[i] == Double.NEGATIVE_INFINITY || dataArray[i] == Double.POSITIVE_INFINITY || Double.isNaN(dataArray[i]))
                continue;
            
            if( min > dataArray[i])
                min = dataArray[i];
            if(max < dataArray[i])
                max = dataArray[i];
        }
        
        minMax[0] = min;
        minMax[1] = max;
        return minMax;
    }
    
    
    /**
     * 返回最值及该元素的序号
     * @param dataArray 
     * @param toCalcMaxValue  true-最大值, false-最小值
     * @return 数组，两个元素，元素1-最值，元素2-最值的序号
     */
    public static double[] getMinMaxAndIndexValueOfArray(double [] dataArray, boolean toCalcMaxValue){
        double[] minMax = new double[2];
        double min = 99999, max = Global.NULL_DOUBLE_VALUE;
        int minIndex = -1, maxIndex = -1;
        for (int i = 0; i < dataArray.length; i++) {
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;

            if(dataArray[i] == Double.NEGATIVE_INFINITY || dataArray[i] == Double.POSITIVE_INFINITY || Double.isNaN(dataArray[i]))
                continue;

            min = dataArray[i];
            max = dataArray[i];
            minIndex = i;
            maxIndex = i;
            break;
        }
        for (int i = 0; i < dataArray.length; i++) {
            if(dataArray[i] == Double.NEGATIVE_INFINITY || dataArray[i] == Double.POSITIVE_INFINITY || Double.isNaN(dataArray[i]))
                continue;
            if( Math.abs(dataArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            if( Math.abs(dataArray[i] - (-9999.0)) < Global.EPS)
                continue;
            if( min > dataArray[i]){
                min = dataArray[i];
                minIndex = i;
            }
            if(max < dataArray[i]){
                max = dataArray[i];
                maxIndex = i;
            }
        }
        if(toCalcMaxValue){
            minMax[0] = max;
            minMax[1] = maxIndex;
        } else {
            minMax[0] = min;
            minMax[1] = minIndex;
        }
        return minMax;
    }
    //求取数组元素的和
    public static double sumup(double[] valueArray, int beginIndex, int endIndex){
        //判断两个序号都是合理的
        
        //求取指定数组元素的和
        double sumup = 0.0f;
        for (int i = beginIndex; i <= endIndex; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            sumup = sumup + valueArray[i];
        }
        return sumup;
    }
    
    /**
     * 求取数组元素中指定段的算术平均值
     * @param valueArray   数组
     * @param beginIndex   数组中指定数据段的起始序号
     * @param endIndex
     * @return 
     */
    public static double mean(double[] valueArray, int beginIndex, int endIndex){
        int count = 0;
        //求取指定数组元素的和
        double sumup = 0.0f;
        if(beginIndex < 0)
            beginIndex = 0;
        if(endIndex < 0 || endIndex >= valueArray.length)
            endIndex = valueArray.length - 1;
        
        for (int i = beginIndex; i <= endIndex; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS || Math.abs(valueArray[i] + 9999.0) < Global.EPS)
                continue;
            if(Double.isInfinite(valueArray[i]) || Double.isNaN(valueArray[i]))
                continue;
            sumup = sumup + valueArray[i];
            count++;
        }
        return sumup/count;        
    }
    
    /**
     * 求取数组元素中指定段的算术平均值
     * @param valueArray   数组
     * @param beginIndex   数组中指定数据段的起始序号
     * @param size         指定数据段的元素个数
     * @return 
     */
    public static double mean2(double[] valueArray, int beginIndex, int size){
        int count = 0;
        //求取指定数组元素的和
        double sumup = 0.0f;
        if(beginIndex < 0)
            beginIndex = 0;
        if(size < 0 || size > valueArray.length - beginIndex)
            size = valueArray.length - beginIndex;
        
        for (int i = beginIndex; i < beginIndex + size; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS || Math.abs(valueArray[i] + 9999.0) < Global.EPS)
                continue;
            if(Double.isInfinite(valueArray[i]) || Double.isNaN(valueArray[i]))
                continue;
            sumup = sumup + valueArray[i];
            count++;
        }
        return sumup/count;        
    }
    
    /***
     * 针对目标数组，利用滑动窗求取各个位置上的算数平均值，形成一个数组
     * @param valueArray  目标数组
     * @param windowSize  滑动窗的半窗长。    滑动窗长 = 2 * 半窗长 + 1
     * @return 
     */
    public static double[] getMeanArray(double[] valueArray, int halfWindowSize){
        int length = valueArray.length;
        double[] result = new double[length];
        int window = 2 * halfWindowSize + 1;
        for (int i = 0; i < halfWindowSize; i++) {
            result[i] = Global.NULL_DOUBLE_VALUE;
            result[length - i - 1] = Global.NULL_DOUBLE_VALUE;
        }
        
        for (int i = halfWindowSize; i < length - halfWindowSize ; i++) {
            result[i] = GeneralDataArrayFunction.mean(valueArray, i - halfWindowSize, i + halfWindowSize);
        }
        return result;
    }
    
    //获取标准差
    public static double getStandardDeviation(double[] valueArray){
        double mean = mean(valueArray, 0, valueArray.length-1);
        double value = 0.0;
        for (int i = 0; i < valueArray.length; i++) {
            value += Math.pow(valueArray[i]-mean, 2);
        }
        return Math.sqrt( value/(valueArray.length-1) );
    }
    
    //获取标准差
    /**
     * 误差平方和的平均数
     * @param valueArray
     * @return 
     */
    public static double getStandardDeviation2(double[] valueArray){
        double mean = mean(valueArray, 0, valueArray.length-1);
        double value = 0.0;
        for (int i = 0; i < valueArray.length; i++) {
            value += Math.pow(valueArray[i]-mean, 2);
        }
        return Math.sqrt( value / valueArray.length );
    }
    
    //获取正态概率纸上制图所需的值
    // x' = (x - x")/a    其中：x"为样本数组的算术平均值，a为样本点的标准差
    public static double[] getNormalFreqValueX(double[] valueArray){
        int valueCount = valueArray.length;
        double[] values = new double[valueCount];
        System.arraycopy(valueArray, 0, values, 0, valueCount);        
        Arrays.sort(values);
        double[] resultValues = new double[valueCount];
        for (int i = 0; i < valueArray.length; i++) {
            double element = valueArray[i];
            for (int j = 0; j < values.length; j++) {
                if(Math.abs( values[j] - element) < Global.EPS){
                    resultValues[i] = (j+1)/(valueCount + 1.0)*100;
                    break;
                }
            }
        }
        //通过逐步逼近，反解累积频率微积分公式中的X值，用来提供交会图绘图
        final double PI = 3.1415926536;
        int number50 = 50;
        int number100 = 100;
        int number500 = 500;
        double startX, startValue;  //从这个 x 值处开始求取微积分，比较是否接近目标值
        if(valueCount <= number50){
            startX = -2.1;
            startValue = 0.01786;
        }else if(valueCount <= number100){
            startX = -2.33;
            startValue = 0.009903;
        }else if(valueCount <= number500){
            startX = -2.9;
            startValue = 0.001866;
        }else{
            startX = -4.0;
            startValue = 0.00003167;
        }
        //
        double deltaX = 0.001;   //x 的前进步长
        double tempX = startX, tmpFreq = startValue;
        for (int i = 0; i < resultValues.length; i++) {
            while(true){
                tmpFreq = tmpFreq + Math.exp(-0.5 * tempX * tempX) * deltaX / Math.sqrt(2*PI);
                tempX += deltaX;
                if(tmpFreq > resultValues[i]/100)
                    break;
            }
            resultValues[i] = tempX;
            tempX = startX;
            tmpFreq = startValue;
        }
        
        return resultValues;
    }
       
    //数据归一化
    public static double[] stdDeviationNormalization(double [] valueArray){
        int length = valueArray.length;
        double[] values = new double[length];
        //求取平均值
        double mean = mean(valueArray, 0, valueArray.length-1);
        //计算标准差
        double tmp = 0.0;
        for (int i = 0; i < length; i++) {
            tmp += Math.pow(valueArray[i]-mean, 2);
        }
        double stdDeviation = Math.sqrt( tmp/(valueArray.length-1) );
        //计算归一化数值
        for (int i = 0; i < length; i++) {
            values[i] = (valueArray[i] - mean)/stdDeviation;
        }
        return values;
    }
    
    /**
     * 数组中指定段的标准差。如果起始序号为-1，则计算整个数组；
     * @param valueArray
     * @param beginIndex 起始序号
     * @param endIndex  结束序号
     * @return 
     */
    public static double getStandardDeviation(double [] valueArray, int beginIndex, int endIndex){
        if(beginIndex == -1){
            beginIndex = 0;
            endIndex = valueArray.length - 1;
        }
        double average = GeneralDataArrayFunction.mean(valueArray, beginIndex, endIndex);
        double sumup = 0;
        int count = endIndex - beginIndex + 1;
        for (int i = beginIndex; i <= endIndex; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS ||  Math.abs(valueArray[i] + 9999.0) < Global.EPS ){
                continue;
            }
            sumup = sumup + (valueArray[i] - average) * (valueArray[i] - average);
        }
        return (double)Math.sqrt(sumup / count);
    }

    /**
     * 求某数组的均方差数组。各个元素在滑动窗内均方差形成的数组
     * @param valueArray
     * @param halfWindow 滑动窗的半长
     * @return 
     */
    public static double[] getStandardDeviationArray(double[] valueArray, int halfWindow){
        int count = valueArray.length;
        double[] stdDeviation = new double[count];
        int window = 2 * halfWindow + 1;
        int size = Math.min(count, halfWindow);
        for (int i = 0; i < size; i++) {
            stdDeviation[i] = 0;                //Global.NULL_DOUBLE_VALUE;
            stdDeviation[count - i - 1] = 0;    //Global.NULL_DOUBLE_VALUE;
        }

        for (int i = 0; i < count - window; i++) {
            stdDeviation[i + halfWindow] = GeneralDataArrayFunction.getStandardDeviation(valueArray, i, i + window -1);
        }
        
        return stdDeviation;
    }
    
       /**
     * 计算两个等长度数据串的相对误差
     * @param dataArray1  数组1
     * @param stId        数组1中数据串的起点
     * @param dataArray2  数组2
     * @param stID2       数组2中数据串的起点
     * @param size        两个等长数据串的长度
     * @return  三个元素的数组，三个元素分别是：最小相对误差，最大相对误差，平均相对误差；
     */
    public static double[] calcRelativeError(double[] dataArray1, int stId, double[] dataArray2, int stID2, int size){
        double[] result = null;
        if(dataArray1.length - stId + 1 < size || dataArray2.length - stID2 + 1 < size)
           return null;
        double min = 99999, max = -99999, average = 0.;
        double tmp, total = 0.;
        for (int i = 0; i < size; i++) {
            tmp = Math.abs(dataArray2[stID2 + i] / dataArray1[stId + i] - 1);
            if(tmp < min)
                min = tmp;
            if(tmp > max)
                max = tmp;
            total += tmp;
        }
        result = new double[]{min, max, (total/size) };
        return result; 
    }

    
    /**
     * 计算数组中指定段的方差。如果起始序号为-1，则计算整个数组；
     * @param valueArray
     * @param bgnIndex
     * @param endIndex
     * @return 
     */
    public static double getDeviationVar(double[] valueArray, int bgnIndex, int endIndex){
        if(bgnIndex  == -1){
            bgnIndex = 0;
            endIndex = valueArray.length - 1;
        }
        double average = GeneralDataArrayFunction.mean(valueArray, bgnIndex, endIndex);
        double sumup = 0;
        int count = endIndex-bgnIndex+1;
        for (int i = bgnIndex; i <= endIndex; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS ||  Math.abs(valueArray[i] - -9999.0) < Global.EPS ){
                count--;
                continue;
            }
            sumup = sumup + (valueArray[i] - average) * (valueArray[i] - average);
        }
        return (sumup/(count-1));
    }
    
    /**
     * 计算两个数组的协方差。如果两个数组不等长，则按短数组的长度截取长数组并计算
     * @param arrayX
     * @param arrayY
     * @return 
     */
    public static double getCovariance(double[] arrayX, double[] arrayY){
        int count = Math.min(arrayX.length, arrayY.length);
        if(count == 0)
            return 0.0;
        double averageX = GeneralDataArrayFunction.mean(arrayX, 0, count-1);
        double averageY = GeneralDataArrayFunction.mean(arrayY, 0, count-1);
        double sumup = 0.;
        for (int i = 0; i < count; i++) {
            sumup += (arrayX[i] - averageX) * (arrayY[i]-averageY);
        }
        return (sumup / (count - 1));
    }
    
    /**
     * 计算两个数组的相关系数
     * @param arrayX
     * @param arrayY
     * @return 
     */
    public static double getCorelationCoefficient(double[] arrayX, double[] arrayY){
        double covariance = getCovariance(arrayX, arrayY);
        double tmp = Math.sqrt(getDeviationVar(arrayX, -1, -1) * getDeviationVar(arrayY, -1, -1));
        if(tmp != 0.)
            return (covariance / tmp);
        else
            return Global.NULL_DOUBLE_VALUE;
    }
    
    /**
     * 求指定序号的数组元素的n次方的和。
     * @param valueArray 目标数组
     * @param beginIndex 目标数组中起始元素的序号
     * @param endIndex   目标数组中结束元素的序号
     * @param power      次方
     * @return 
     */
    public static double sumupOfElementPower(double[] valueArray, int beginIndex, int endIndex, int power){
        //判断两个序号都是合理的
        
        //求取指定数组元素的和
        double sumup = 0.0f;
        for (int i = beginIndex; i <= endIndex; i++) {
            if( Math.abs(valueArray[i] - Global.NULL_DOUBLE_VALUE) < Global.EPS)
                continue;
            sumup = sumup + Math.pow(valueArray[i], power);
        }
        return sumup;
    }
    
    public static double getDotMultiplyArray(double[] arrayA, double[] arrayB){
        double sumup = 0.0;
        if(arrayA.length  != arrayB.length)
            return sumup;
        for (int i = 0; i < arrayB.length; i++) {
            sumup = sumup + arrayA[i] * arrayB[i];
        }
        return sumup;
    }

    /**
     * 对二维数组进行行列转换
     * @param dataArray
     * @return 
     */
    public static double[][] transformArray(double[][] dataArray){
        int rowSize = dataArray.length;
        int columnSize = dataArray[0].length;
        double[][] newArray = new double[columnSize][rowSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                newArray[j][i] = dataArray[i][j];
            }
        }
        return newArray;
    }

    /**
     * 变差函数计算（步长=1）
     * 计算给定数组的变差函数值，计算过程中：步长为1个采样间隔，忽略测井值为无效值的采样点。
     * value = {Σ（x[i]-x[i+h]）^2}/2n
     * @param dataArray   测井曲线数组
     * @param step        计算变差函数值所需的样点间隔数   =1表示前后两点直接计算，=2表示间隔1个点
     * @return 
     */
    public static double getVariationArray(double[] dataArray, int step){
        int length = dataArray.length;
        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray[i] == Global.NULL_DOUBLE_VALUE || Double.isInfinite(dataArray[i]) || Double.isNaN(dataArray[i]) ){
                length = length - 1;
            }
        }
        int countN = length - step;     //可以计算变差函数值的样点个数
        
        double nextValue = 0;
        double squareValue = 0;
        for (int i = 0; i < dataArray.length - step; i++) {
            if (dataArray[i] != Global.NULL_DOUBLE_VALUE && !Double.isInfinite(dataArray[i]) && !Double.isNaN(dataArray[i]) ){
                nextValue = Global.NULL_DOUBLE_VALUE;
                for (int j = i+step; j < dataArray.length; j++) {
                    if (dataArray[i] != Global.NULL_DOUBLE_VALUE && !Double.isInfinite(dataArray[i]) && !Double.isNaN(dataArray[i]) ){
                        nextValue = dataArray[j];
                        break;
                    }
                }
                if (dataArray[i] == Global.NULL_DOUBLE_VALUE || Double.isInfinite(dataArray[i]) || Double.isNaN(dataArray[i]) ){
                    continue;
                }
                squareValue +=Math.pow(dataArray[i] - nextValue, 2);
            }
        }
        return squareValue / countN / 2;
    }
    
    
}
