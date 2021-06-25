/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.utils.wrapper;

import com.cif.utils.constant.Global;
import com.cif.utils.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 一维曲线数据体读写类（二进制形式，开头留出128个字节作为扩展）
 *
 * @author xsj
 */
public class Curve1DRwWrapper extends CurveRwWrapper {

    public Curve1DRwWrapper(String filePath) {
        super(filePath);
    }

    //startDataIndex:<0,新文件向前偏移index个数据点
    public void changeDepthRange(byte dataType, int startDataIndex, int endDataIndex, boolean preserved) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        String tmpFilePath = PathUtil.getFullPath(filePath) + PathUtil.getBaseName(filePath)
                + "_tmp." + PathUtil.getExtension(filePath);
        File tmpFile = new File(tmpFilePath);

        if (preserved) {
            try {
                FileChannel tmpFileChannel = new RandomAccessFile(tmpFilePath, "rw").getChannel();
                byte dataTypeSize = Global.sizeof(dataType);
                int offset = 0;
                int dataCount = 0;
                //新的起始深度大于老的起始深度
                if (startDataIndex >= 0) {
                    offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
                    dataCount = endDataIndex - startDataIndex + 1;
                } else {
                    offset = FILE_HEAD_EMPTY_BLOCK_SIZE;
                    dataCount = endDataIndex + 1;
                }
                int bufferSize = dataTypeSize * dataCount;
                fileChannel.position(offset);
                ByteBuffer buffer = getInitBuffer(bufferSize);
                fileChannel.read(buffer);
                buffer.rewind();

                switch (dataType) {
                    case Global.DATA_BYTE:
                        byte[] byteData = new byte[dataCount];
                        buffer.get(byteData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, byteData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, byteData);
                        }
                        break;
                    case Global.DATA_SHORT:
                        short[] shortData = new short[dataCount];
                        buffer.asShortBuffer().get(shortData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, shortData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, shortData);
                        }
                        break;
                    case Global.DATA_INT:
                        int[] intData = new int[dataCount];
                        buffer.asIntBuffer().get(intData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, intData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, intData);
                        }
                        break;
                    case Global.DATA_LONG:
                        long[] longData = new long[dataCount];
                        buffer.asLongBuffer().get(longData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, longData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, longData);
                        }
                        break;
                    case Global.DATA_FLOAT:
                        float[] floatData = new float[dataCount];
                        buffer.asFloatBuffer().get(floatData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, floatData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, floatData);
                        }
                        break;
                    case Global.DATA_DOUBLE:
                        double[] doubleData = new double[dataCount];
                        buffer.asDoubleBuffer().get(doubleData, 0, dataCount);
                        if (startDataIndex > 0) {
                            writeData(tmpFileChannel, 0, dataCount, doubleData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, doubleData);
                        }
                        break;
                }

                tmpFileChannel.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        close();
        File file = new File(filePath);
        file.delete();
        tmpFile.renameTo(file);
    }

    public int readData(int startDataIndex, int dataCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "r").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.get(data, 0, dataCount);
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "r").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.asShortBuffer().get(data, 0, dataCount);
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "r").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.asIntBuffer().get(data, 0, dataCount);
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "r").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.asLongBuffer().get(data, 0, dataCount);
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.asFloatBuffer().get(data, 0, dataCount);
            fileChannel.close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
            int bufferSize = dataTypeSize * dataCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            buffer.asDoubleBuffer().get(data, 0, dataCount);
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public boolean writeData(int startDataIndex, int dataCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, byte[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, short[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.asShortBuffer().put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, int[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.asIntBuffer().put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, long[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.asLongBuffer().put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, float[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.asFloatBuffer().put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount, double[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        buffer.asDoubleBuffer().put(data, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize;
        return writeData(channel, offset, buffer);
    }

    private boolean writeData(FileChannel channel, int offset, ByteBuffer buffer) {
        try {
            channel.position(offset);
            buffer.flip();
            channel.write(buffer);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Curve1DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
