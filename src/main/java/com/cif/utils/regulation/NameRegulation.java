/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.regulation;

import com.cif.utils.constant.Global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 @author wangcaizhi 
 2014.6.12
 1、去除关键字，固定的表名字，深度索引曲线名字
 2、允许的特殊字符：~  @ $  . _ 
 3、开始不允许数字，开始必须为字母或特殊字符
 4、长度限制31
 5、两端无空格
 */
public class NameRegulation {

    private final HashSet<String> keyLoggingNameSet = new HashSet<String>();
//    private char[] invalidLoggingChars = {'`', '!', '#', '%', '^', '&', '*', '(', ')', '-', '+', '=',
//        '{', '[', '}', ']', '|', '\\', ':', ';', '"', '\'', '<', ',', '>', '?', '/', '\n', '\t'};
    private char[] invalidLoggingChars = {'`', '!', '#', '%', '^', '&', '*', '(', ')', '+', '=',
        '{', '[', '}', ']', '|', '\\', ':', ';', '"', '\'', '<', ',', '>', '?', '/', '\n', '\t'};
    private boolean lastErrorCreated = false;
    private String lastError;
    private final static String ERROR_OUTOF_LENGTH = java.util.ResourceBundle.getBundle(
            "cif/regulation/Bundle").getString("NameRegulation.errorOutOfLength");
    private final static String ERROR_KEY_WORD_CONFLICT = java.util.ResourceBundle.getBundle(
            "cif/regulation/Bundle").getString("NameRegulation.ErrorKeyWordConflict");
    private final static String ERROR_BOTH_ENDS_INCLUDE_BLANK = java.util.ResourceBundle.getBundle(
            "cif/regulation/Bundle").getString("NameRegulation.errorBothEndsIncludeBlank");
    private final static String ERROR_INCLUDE_INVALID_CHAR = java.util.ResourceBundle.getBundle(
            "cif/regulation/Bundle").getString("NameRegulation.ErrorIncludeInvalidChar");
    private final static String ERROR_START_WITH_DIGITAL = java.util.ResourceBundle.getBundle(
            "cif/regulation/Bundle").getString("NameRegulation.ErrorStartWithDigital");

    public NameRegulation() {
        String keyNameFilePath = Global.getInstallationResourcePath(Global.PATH_REGULATION)
                + File.separator + "KeyLoggingNames.txt"; //NOI18N
        loadKeyNames(keyNameFilePath);
        Arrays.sort(invalidLoggingChars);
    }

    public boolean isValidLoggingName(String loggingName) {
        lastErrorCreated = true;
        if (getByteLength(loggingName) > (Global.CIFPLUS_MAX_STRING_LENGTH + 1) * 2) {
            lastError = ERROR_OUTOF_LENGTH;
            return false;
        }
        if (keyLoggingNameSet.contains(loggingName)) {
            lastError = ERROR_KEY_WORD_CONFLICT;
            return false;
        }
        int length = loggingName.length();
        if (loggingName.charAt(0) == ' ' || loggingName.charAt(length - 1) == ' ') {
            lastError = ERROR_BOTH_ENDS_INCLUDE_BLANK;
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = loggingName.charAt(i);
            if (Arrays.binarySearch(invalidLoggingChars, c) >= 0) {
                lastError = ERROR_INCLUDE_INVALID_CHAR + c;
                return false;
            }
        }
        char c = loggingName.charAt(0);
        if (c >= '0' && c <= '9') {
            lastError = ERROR_START_WITH_DIGITAL;
            return false;
        }
        return true;
    }

    public String getLastError() {
        if (lastErrorCreated) {
            return lastError;
        } else {
            return ""; //NOI18N
        }
    }

    private int getByteLength(String str) {

        try {
            return str.getBytes("Unicode").length; //NOI18N
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NameRegulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean isValidWellName(String wellName) {
        boolean isValid = isValidFileName(wellName);
        if (isValid) {
            lastErrorCreated = false;
        } else {
            lastErrorCreated = true;
            lastError = ERROR_INCLUDE_INVALID_CHAR;
        }
        return isValid;
    }

    public boolean isValidCategoryName(String categoryName) {
        boolean isValid = isValidFileName(categoryName);
        if (isValid) {
            lastErrorCreated = false;
        } else {
            lastErrorCreated = true;
            lastError = ERROR_INCLUDE_INVALID_CHAR;
        }
        return isValid;
    }

    private void loadKeyNames(String keyNameFilePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(keyNameFilePath));
            String data = br.readLine();
            while (data != null) {
                keyLoggingNameSet.add(data.trim().toLowerCase());
                data = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameRegulation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NameRegulation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(NameRegulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isValidFileName(String filePath) {
        File f = new File(filePath);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
