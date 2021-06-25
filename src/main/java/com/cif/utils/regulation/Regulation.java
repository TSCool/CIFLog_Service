/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.regulation;

/**
 *
 * @author wangcaizhi 2014.6.12
 */
public class Regulation {

    private static NameRegulation nameInstance;
    private static TableRegulation tableInstance;

    public static synchronized NameRegulation getNameDefault() {
        if (nameInstance == null) {
            nameInstance = new NameRegulation();
        }
        return nameInstance;
    }

    public static synchronized TableRegulation getTableDefault() {
        if (tableInstance == null) {
            tableInstance = new TableRegulation();
        }
        return tableInstance;
    }
}
