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
public class DoubleArray extends NumberValueArray {

    private double[] array;
    private int increaseSize;

    public DoubleArray() {
        this(10, 10);
    }

    public int getIncreaseSize() {
        return increaseSize;
    }

    public void setIncreaseSize(int increaseSize) {
        this.increaseSize = increaseSize;
    }

    public DoubleArray(int initialSize, int increaseSize) {
        array = new double[initialSize];
        this.increaseSize = increaseSize;
        _size = 0;
        _capacity = initialSize;
    }

    public void add(double data) {
        if (_size >= array.length) {
            _capacity = array.length + increaseSize;
            double[] arrayN = new double[_capacity];
            System.arraycopy(array, 0, arrayN, 0, array.length);
            array = arrayN;
        }
        array[_size++] = data;
    }

    public void add(int index, double data) {
        if (index > array.length) {
            throw new ArrayIndexOutOfBoundsException(index + " > " + array.length);
        }
        double[] arrayN = null;
        if (_size >= array.length) {
            _capacity = array.length + increaseSize;
            arrayN = new double[_capacity];
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

    public final double get(int index) {
        return array[index];
    }

    @Override
    public final Number getValue(int index) {
        return array[index];
    }

    public final void set(int index, double value) {
        this.array[index] = value;
    }

    public final void clear() {
        _size = 0;
    }

    public double[] getArray() {
        final double[] a = new double[_size];
        System.arraycopy(array, 0, a, 0, _size);
        return a;
    }

    public void setCapacity(int newCapacity) {
        _capacity = newCapacity;
        final double[] newArray = new double[_capacity];
        _size = Math.min(_capacity, _size);
        System.arraycopy(array, 0, newArray, 0, _size);
        array = newArray;
    }

    public void setArray(double[] a) {
        if (array.length < a.length) {
            setCapacity(a.length);
        }
        System.arraycopy(a, 0, array, 0, a.length);
        _size = a.length;
    }

    public void setArray(double[][] a) {
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
    public DoubleArray clone() {
        DoubleArray a = new DoubleArray(_size, increaseSize);
        System.arraycopy(array, 0, a.array, 0, _size);
        a._size = _size;
        return a;
    }

    @Override
    public String[] toStringArray() {
        String[] stringArray = new String[_size];
        for (int i = 0; i < getSize(); i++) {
            stringArray[i] = Double.toString(array[i]);
        }
        return stringArray;
    }

    @Override
    public void parseStringArray(String[] array) {
        clear();
        for (int i = 0; i < array.length; i++) {
            double v = Global.NULL_DOUBLE_VALUE;
            try {
                v = Double.parseDouble(array[i]);
            } catch (NumberFormatException e) {
            }
            add(v);
        }
    }
}
