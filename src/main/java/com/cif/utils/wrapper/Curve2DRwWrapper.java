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
 *
 * @author xsj
 */
public class Curve2DRwWrapper extends CurveRwWrapper {

    public Curve2DRwWrapper(String filePath) {
        super(filePath);
    }

    //startDataIndex:<0,新文件向前偏移index个数据点
    public void changeDepthRange(byte dataType, int startDataIndex, int endDataIndex,
            int timeSampleCount, boolean preserved) {
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
                int dataCount = 0;
                //新的起始深度大于老的起始深度
                if (startDataIndex >= 0) {
                    dataCount = endDataIndex - startDataIndex + 1;
                } else {
                    dataCount = endDataIndex + 1;
                }

                switch (dataType) {
                    case Global.DATA_BYTE:
                        byte[][] byteData = new byte[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, timeSampleCount, byteData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, byteData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, byteData);
                        }
                        break;
                    case Global.DATA_SHORT:
                        short[][] shortData = new short[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 1, dataCount, timeSampleCount, shortData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, shortData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, shortData);
                        }
                        break;
                    case Global.DATA_INT:
                        int[][] intData = new int[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 1, dataCount, timeSampleCount, intData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, intData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, intData);
                        }
                        break;
                    case Global.DATA_LONG:
                        long[][] longData = new long[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 1, dataCount, timeSampleCount, longData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, longData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, longData);
                        }
                        break;
                    case Global.DATA_FLOAT:
                        float[][] floatData = new float[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 1, dataCount, timeSampleCount, floatData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, floatData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, floatData);
                        }
                        break;
                    case Global.DATA_DOUBLE:
                        double[][] doubleData = new double[dataCount][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 1, dataCount, timeSampleCount, doubleData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, timeSampleCount, doubleData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, timeSampleCount, doubleData);
                        }
                        break;
                }

                tmpFileChannel.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        close();
        File file = new File(filePath);
        file.delete();
        tmpFile.renameTo(file);
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, byte[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            byte[] srcData = new byte[dataCount * timeSampleCount];
            buffer.get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
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

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, short[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            short[] srcData = new short[dataCount * timeSampleCount];
            buffer.asShortBuffer().get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
            short[] srcData = new short[count];
            buffer.asShortBuffer().get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, int[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            int[] srcData = new int[dataCount * timeSampleCount];
            buffer.asIntBuffer().get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
            int[] srcData = new int[count];
            buffer.asIntBuffer().get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, long[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            long[] srcData = new long[dataCount * timeSampleCount];
            buffer.asLongBuffer().get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
            long[] srcData = new long[count];
            buffer.asLongBuffer().get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, float[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            float[] srcData = new float[dataCount * timeSampleCount];
            buffer.asFloatBuffer().get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;
            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
            float[] srcData = new float[count];
            buffer.asFloatBuffer().get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, double[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            double[] srcData = new double[dataCount * timeSampleCount];
            buffer.asDoubleBuffer().get(srcData);

            if (data.length < dataCount) {
                dataCount = data.length;
            }
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                System.arraycopy(srcData, position, data[i], 0, timeSampleCount);
                position += timeSampleCount;
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();

            int count = dataCount * timeSampleCount;
            double[] srcData = new double[count];
            buffer.asDoubleBuffer().get(srcData);
            System.arraycopy(srcData, 0, data, 0, count > data.length ? data.length : count);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    private boolean writeData(FileChannel channel, int offset, ByteBuffer buffer) {
        try {
            channel.position(offset);
            buffer.flip();
            channel.write(buffer);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, byte[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, byte[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        byte[] dataBuffer = new byte[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, byte[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, short[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, short[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        short[] dataBuffer = new short[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.asShortBuffer().put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, short[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asShortBuffer().put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, int[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, int[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        int[] dataBuffer = new int[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.asIntBuffer().put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, int[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asIntBuffer().put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, long[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, long[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        long[] dataBuffer = new long[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.asLongBuffer().put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, long[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asLongBuffer().put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, float[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, float[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        float[] dataBuffer = new float[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.asFloatBuffer().put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, float[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asFloatBuffer().put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, double[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, double[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        double[] dataBuffer = new double[dataCount * timeSampleCount];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            System.arraycopy(data[i], 0, dataBuffer, position, timeSampleCount);
            position += timeSampleCount;
        }
        buffer.asDoubleBuffer().put(dataBuffer, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve2DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int timeSampleCount, double[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asDoubleBuffer().put(data, 0, dataCount * timeSampleCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex * dataTypeSize * timeSampleCount;
        return writeData(channel, offset, buffer);
    }
}
