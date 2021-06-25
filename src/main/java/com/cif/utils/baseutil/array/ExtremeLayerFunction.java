/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.baseutil.array;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class ExtremeLayerFunction {

    /**
     * 获取极值方差分层指标函数的各个取值
     * @param dataArray1
     * @param dataArray2
     * @return
     */
    public static double[] getExtremeLayerIndexArray(double[] dataArray1, double[] dataArray2){
        if(dataArray1 == null)
            return dataArray1;
        
        int dataCount = dataArray1.length;
        double[] indexArray = new double[dataCount-1];
        for (int i = 0; i < dataCount-1; i++) {
            double sumup11 = GeneralDataArrayFunction.sumup(dataArray1, 0, i);            
            double sumup12 = GeneralDataArrayFunction.sumup(dataArray1, i+1, dataCount-1);
            double averageFront1 = sumup11 * sumup11 / (i+1);
            double averageBack1 = sumup12 * sumup12 / (dataCount-i-1);
            
            double sumup21 = GeneralDataArrayFunction.sumup(dataArray2, i, i);
            double sumup22 = GeneralDataArrayFunction.sumup(dataArray2, i+1, dataCount-1);
            double averageFront2 = sumup21 * sumup21 / (i+1);            
            double averageBack2 = sumup22 * sumup22 / (dataCount-i-1);
            
//            float sumupPower1 = ExtremeLayerFunction.sumupOfelementPower(dataArray1, 0, i, 2);
//            float sumupPower2 = ExtremeLayerFunction.sumupOfelementPower(dataArray2, 0, i, 2);
            indexArray[i] = (averageFront1 + averageBack1);  //      /(sumupPower - averageFront - averageBack);
            
//            float all_up = (averageFront1 + averageFront2 + averageFront2 + averageBack2);
            //indexArray[i] = all_up /(sumupPower1+sumupPower2 - all_up);
        }
        return indexArray;
    }
    
    /**
     * 获取数组dataArray各个元素的极值所在序号
     * @param dataArray  一个数组元素数列的极值
     * @return 序号数组  该数组元素是dataArray数组中极值元素所在的序号
     */
    public static ArrayList<Integer> getExtremeIndexArray(double[] dataArray){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 2; i < dataArray.length-2; i++) {
            if( (dataArray[i-1] - dataArray[i-2]) * (dataArray[i] - dataArray[i-1])>0 && 
                    ((dataArray[i+1] - dataArray[i]) * (dataArray[i+2] - dataArray[i+1])>0)){
                if( (dataArray[i] - dataArray[i-1])*(dataArray[i+1] - dataArray[i]) < 0){
                    indexList.add(i);
                }
            }
        }
        return indexList;
    }
    
    /**
     * 求取数组离散序列的极大值（或极小值）所在的位置（数组序号）
     * @param dataArray
     * @param thresholdValue 阈值： 对于极大值，忽略小于该阈值的极大值；忽略大于该阈值的极小值；
     * @param isPositive 真-极大值； 假-极小值
     * @return 
     */
    public static ArrayList<Integer> getExtremeIndexArray(double[] dataArray, double thresholdValue, boolean isPositive){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        if(isPositive){     //求极大值
            for (int i = 2; i < dataArray.length-3; i++) {
                if(dataArray[i] < thresholdValue){      //如果当前元素取值小于阈值，则不进行运算
                    continue;
                }
                
                if( (dataArray[i] - dataArray[i-1])>0 && ((dataArray[i+1] - dataArray[i])<0) &&
                     (dataArray[i-1]-dataArray[i-2]>0) && ((dataArray[i+2]-dataArray[i+1]<0)) ) {
                    indexList.add(i);
                }
            }
        } else {    //求极小值
            for (int i = 2; i < dataArray.length-3; i++) {
                if(dataArray[i] > thresholdValue)       //如果当前元素取值大于阈值，则不进行运算
                    continue;
                
                if( (dataArray[i] - dataArray[i-1])<0 && ((dataArray[i+1] - dataArray[i])>0) &&
                     (dataArray[i-1]-dataArray[i-2]<0) && ((dataArray[i+2]-dataArray[i-1]>0))){
                    indexList.add(i);
                }
            }
        }
        
        return indexList;
    }
    public static double[] getExtremeArray(double[] dataArray, boolean isMax){
        ArrayList<Double> extremeArray = new ArrayList<Double>();
        if(isMax){
            for (int i = 2; i < dataArray.length-3; i++) {
                if( (dataArray[i] - dataArray[i-1])>0 && ((dataArray[i+1] - dataArray[i])<0) &&
                    (dataArray[i-1]-dataArray[i-2]>0) && ((dataArray[i+2]-dataArray[i+1]<0)) ) {
                    extremeArray.add(dataArray[i]);
                }
            }
        }else{
            for (int i = 2; i < dataArray.length-3; i++) {
                if( (dataArray[i] - dataArray[i-1])<0 && ((dataArray[i+1] - dataArray[i])>0) &&
                     (dataArray[i-1]-dataArray[i-2]<0) && ((dataArray[i+2]-dataArray[i-1]>0))){
                    extremeArray.add(dataArray[i]);
                    
                }
            }
        }
        double[] extremes = new double[extremeArray.size()];
        for (int i = 0; i < extremes.length; i++) {
            extremes[i] = extremeArray.get(i);
        }

        return extremes;
    }
}
