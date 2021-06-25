/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.baseutil.array;

/**
 * 为数值类型读取时相互转换提供支持
 *
 * @author 高学亮
 */
public abstract class NumberValueArray extends ValueArray {

    public abstract Number getValue(int index);
}
