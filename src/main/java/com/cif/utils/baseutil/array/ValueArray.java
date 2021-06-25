/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.baseutil.array;

/**
 *
 * @author wangcz
 */
public abstract class ValueArray implements Cloneable{

    protected int _size;
    protected int _capacity;

    public int get_size() {
        return _size;
    }

    public void set_size(int _size) {
        this._size = _size;
    }

    public int get_capacity() {
        return _capacity;
    }

    public void set_capacity(int _capacity) {
        this._capacity = _capacity;
    }

    public int getSize() {
        return _size;
    }

    public int getCapacity() {
        return _capacity;
    }

    public abstract void clear();

    public abstract void setCapacity(int newCapacity);

    @Override
    public abstract ValueArray clone();

    public abstract String[] toStringArray();

    public abstract void parseStringArray(String[] array);
}
