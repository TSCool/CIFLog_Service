/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.wrapper;

import com.cif.utils.PathUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xsj
 */
public class CurveRwWrapper {

    public static final int FILE_HEAD_EMPTY_BLOCK_SIZE = 128;
    protected String filePath = null;
    protected ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
    protected FileChannel fileChannel = null;

    public CurveRwWrapper(String filePath) {
        this.filePath = filePath;

        //创建目录
        File dir = new File(PathUtil.getFullPathNoEndSeparator(filePath));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    protected ByteBuffer getInitBuffer(int bufferSize) {
        if (bufferSize > byteBuffer.capacity()) {
            byteBuffer = ByteBuffer.allocate(bufferSize);
        } else {
            byteBuffer.clear();
            byteBuffer.limit(bufferSize);
        }
        return byteBuffer;
    }

    protected void close() {
        if (fileChannel != null) {
            try {
                fileChannel.close();
                fileChannel = null;
            } catch (IOException ex) {
                Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete() {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
