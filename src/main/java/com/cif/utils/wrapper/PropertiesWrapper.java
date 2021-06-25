/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.wrapper;

import com.cif.utils.baseutil.INIFile;

import java.util.Map;

/**
 * 曲线附加属性读写类
 *
 * @author xsj
 */
public class PropertiesWrapper {

    private INIFile iNIFile = null;
    public static final String PROP_SECTION = "Properties";

    public PropertiesWrapper(String filePath) {
        iNIFile = new INIFile(filePath);
    }

    public Map read() {
        return iNIFile.getProperties(PROP_SECTION);
    }

    public void write(Map<String, String> propertiesMap) {
        iNIFile.addSection(PROP_SECTION, "CIFLog System Properties");
        String[] keys = propertiesMap.keySet().toArray(new String[0]);
        for (String key : keys) {
            iNIFile.setStringProperty(PROP_SECTION, key, propertiesMap.get(key), null);
        }
        iNIFile.save();
    }
}
