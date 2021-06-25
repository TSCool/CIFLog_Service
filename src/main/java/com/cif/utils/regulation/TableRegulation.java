/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.regulation;

import com.cif.utils.constant.Global;
import com.cif.utils.baseutil.INIFile;

import java.io.File;

/**
 *
 * @author wangcaizhi 2014.6.12
 */
public class TableRegulation {

    private final INIFile iniFile;

    public TableRegulation() {
        iniFile = new INIFile(Global.getInstallationResourcePath(Global.PATH_REGULATION) + File.separator + "table.ini");
    }

    public String getTableTypeString(String templateName) {
        String resultName = iniFile.getStringProperty(templateName, "__TableType");
        return null == resultName ? templateName : resultName;
    }

    public String getFieldName(String tableType, String fieldName) {
        String resultName = iniFile.getStringProperty(tableType, fieldName);
        return null == resultName ? fieldName : resultName;
    }
}
