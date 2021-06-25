package com.cif.utils.wrapper;

import com.cif.utils.PathUtil;
import com.cif.utils.constant.Global;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocRwWrapper {

    public static final int FILE_HEAD_EMPTY_BLOCK_SIZE = 128;
    private String filePath = null;
    protected FileChannel fileChannel = null;
    protected ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

    public DocRwWrapper(String filePath) {
        this.filePath = filePath;
        //创建目录
        File dir = new File(PathUtil.getFullPathNoEndSeparator(filePath));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public int readData(int startDataIndex, int dataCount,byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount;
            byte[] srcData = new byte[count];
            buffer.get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    private void getDocBufferFileChannel() {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
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

}
