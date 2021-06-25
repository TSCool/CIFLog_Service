/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.baseutil.array;

import com.cif.utils.constant.Global;

/**
 *
 * @author wangcz
 * @modified 2017.2.3, add: add(index, data); remove(index).
 */
public class FloatArray extends NumberValueArray {

    private float[] array;
    private int increaseSize;

    public int getIncreaseSize() {
        return increaseSize;
    }

    public void setIncreaseSize(int increaseSize) {
        this.increaseSize = increaseSize;
    }

    public FloatArray() {
        this(10, 10);
    }

    public FloatArray(int initialSize, int increaseSize) {
        array = new float[initialSize];
        this.increaseSize = increaseSize;
        _size = 0;
        _capacity = initialSize;
    }

    public void add(float data) {
        if (_size >= array.length) {
            _capacity = array.length + increaseSize;
            float[] arrayN = new float[_capacity];
            System.arraycopy(array, 0, arrayN, 0, array.length);
            array = arrayN;
        }
        array[_size++] = data;
    }

    public void add(int index, float data) {
        if (index > array.length) {
            throw new ArrayIndexOutOfBoundsException(index + " > " + array.length);
        }
        float[] arrayN = null;
        if (_size >= array.length) {
            _capacity = array.length + increaseSize;
            arrayN = new float[_capacity];
            System.arraycopy(array, 0, arrayN, 0, index);
        }
        if (arrayN != null) {
            System.arraycopy(array, index, arrayN, index + 1, _size - index);
            array = arrayN;
        } else {
            System.arraycopy(array, index, array, index + 1, _size - index);
        }
        array[index] = data;
        _size++;
    }

    public void remove(int index) {
        if (index >= _size) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + _size);
        }
        int numMoved = _size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        _size--;
    }

    public final float get(int index) {
        return array[index];
    }

    @Override
    public final Number getValue(int index) {
        return array[index];
    }

    public final void clear() {
        _size = 0;
    }

    public float[] getArray() {
        final float[] a = new float[_size];
        System.arraycopy(array, 0, a, 0, _size);
        return a;
    }

    public void setCapacity(int newCapacity) {
        _capacity = newCapacity;
        final float[] newArray = new float[_capacity];
        _size = Math.min(_capacity, _size);
        System.arraycopy(array, 0, newArray, 0, _size);
        array = newArray;
    }

    public void setArray(float[] a) {
        if (array.length < a.length) {
            setCapacity(a.length);
        }
        System.arraycopy(a, 0, array, 0, a.length);
        _size = a.length;
    }

    public void setArray(float[][] a) {
        int aSize = a.length * a[0].length;
        if (array.length < aSize) {
            setCapacity(aSize);
        }
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, array, a[0].length * i, a[0].length);
        }
        _size = aSize;
    }

    @Override
    public FloatArray clone() {
        FloatArray a = new FloatArray();
        a.setArray(this.getArray());
        return a;
    }

    @Override
    public String[] toStringArray() {
        String[] stringArray = new String[_size];
        for (int i = 0; i < getSize(); i++) {
            stringArray[i] = Float.toString(array[i]);
        }
        return stringArray;
    }

    @Override
    public void parseStringArray(String[] array) {
        clear();
        for (int i = 0; i < array.length; i++) {
            float v = Global.NULL_FLOAT_VALUE;
            try {
                v = Float.parseFloat(array[i]);
            } catch (NumberFormatException e) {
            }
            add(v);
        }
    }
}
