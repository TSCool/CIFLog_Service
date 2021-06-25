/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cif.utils.baseutil.array;

import com.cif.utils.constant.Global;

/**
 *
 * @author Liweizhong
 */
public class MathMean {
    
    /**
     * 求取几何平均数
     * @param data
     * @return
     */
    public  static float getGeometricMean(byte[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    public  static float getGeometricMean(short[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    public  static float getGeometricMean(int[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    public  static float getGeometricMean(float[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    public  static float getGeometricMean(long[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    public  static float getGeometricMean(double[] data){
        double product = 1;
        for (int i = 0; i < data.length; i++) {
            product = product * data[i];
        }

        return (float)Math.pow(product, 1.0/data.length);
    }
    /**
     * 求取算术平均值
     * @param data
     * @return
     */
    public  static float getArithmaticMean(byte[] data){
        float sumup = 0.0f;
        for (int i = 0; i < data.length; i++) {
            sumup = data[i] + sumup;
        }

        return sumup/data.length;

    }
    public  static float getArithmaticMean(short[] data){
        float sumup = 0.0f;
        for (int i = 0; i < data.length; i++) {
            sumup = data[i] + sumup;
        }

        return sumup/data.length;

    }
    public  static float getArithmaticMean(int[] data){
        float sumup = 0.0f;
        for (int i = 0; i < data.length; i++) {
            sumup = data[i] + sumup;
        }

        return sumup/data.length;
    }
    public  static float getArithmaticMean(float[] data){
        float sumup = 0.0f;
        for (int i = 0; i < data.length; i++) {
            if(data[i] == Global.NULL_FLOAT_VALUE)
                continue;
            sumup = data[i] + sumup;
        }

        return sumup/data.length;
    }
    public  static float getArithmaticMean(long[] data){
        long sumup = 0;
        for (int i = 0; i < data.length; i++) {
            sumup = data[i] + sumup;
        }

        return (float)(sumup/data.length);

    }
    public  static float getArithmaticMean(double[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = data[i] + sumup;
        }

        return (float)(sumup/data.length);
    }
    
    /**
     * 求取调和平均数
     * @param data
     * @return
     */
    public  static float getHarmonicaMean(byte[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }
    public  static float getHarmonicaMean(short[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }
    public  static float getHarmonicaMean(int[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }
    public  static float getHarmonicaMean(float[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }
    public  static float getHarmonicaMean(long[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }
    public  static float getHarmonicaMean(double[] data){
        double sumup = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumup = sumup + 1.0/data[i];
        }

        return (float)(data.length/sumup);
    }

    /**
     * 求取权重平均数
     * @param data
     * @return
     */
    public  static float getWeightAverage(byte[] data, byte[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;
        
        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }
    public  static float getWeightAverage(short[] data, short[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;

        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }
    public  static float getWeightAverage(int[] data, int[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;

        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }
    public  static float getWeightAverage(float[] data, float[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;

        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }
    public  static float getWeightAverage(long[] data, long[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;

        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }
    public  static float getWeightAverage(double[] data, double[] weight){
        if(data.length != weight.length)
            return Global.NULL_FLOAT_VALUE;

        double sumupValue = 0.0;
        double sumupWeight = 0.0;
        for (int i = 0; i < data.length; i++) {
            sumupValue = sumupValue + data[i] * weight[i];
            sumupWeight = sumupWeight + weight[i];
        }

        return (float)(sumupValue/sumupWeight);
    }

    
    public static float getMax(float[] data){
        if(data.length ==0)
            return Global.NULL_FLOAT_VALUE;
        
        float result = data[0];
        for (int i = 1; i < data.length; i++) {
            if(result < data[i])
                result = data[i];
        }
        return result;
    }
    public static float getMin(float[] data){
        if(data.length ==0)
            return Global.NULL_FLOAT_VALUE;
        
        float result = data[0];
        for (int i = 1; i < data.length; i++) {
            if(result > data[i])
                result = data[i];
        }
        return result;        
    }
        
    public static double getMax(double[] data){
        if(data.length ==0)
            return Global.NULL_FLOAT_VALUE;
        
        double result = data[0];
        for (int i = 1; i < data.length; i++) {
            if(result < data[i])
                result = data[i];
        }
        return result;
    }    
    public static double getMin(double[] data){
        if(data.length ==0)
            return Global.NULL_FLOAT_VALUE;
        
        double result = data[0];
        for (int i = 1; i < data.length; i++) {
            if(result > data[i])
                result = data[i];
        }
        return result;
    }
    
}
