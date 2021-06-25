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
public class Curve3DRwWrapper extends CurveRwWrapper {

    public Curve3DRwWrapper(String filePath) {
        super(filePath);
    }

    //startDataIndex:<0,新文件向前偏移index个数据点
    public void changeDepthRange(byte dataType, int startDataIndex, int endDataIndex,
            int arrayNum, int timeSampleCount, boolean preserved) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
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
                        byte[][][] byteData = new byte[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, byteData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, byteData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, byteData);
                        }
                        break;
                    case Global.DATA_SHORT:
                        short[][][] shortData = new short[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, shortData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, shortData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, shortData);
                        }
                        break;
                    case Global.DATA_INT:
                        int[][][] intData = new int[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, intData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, intData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, intData);
                        }
                        break;
                    case Global.DATA_LONG:
                        long[][][] longData = new long[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, longData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, longData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, longData);
                        }
                        break;
                    case Global.DATA_FLOAT:
                        float[][][] floatData = new float[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, floatData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, floatData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, floatData);
                        }
                        break;
                    case Global.DATA_DOUBLE:
                        double[][][] doubleData = new double[dataCount][arrayNum][timeSampleCount];
                        readData(startDataIndex >= 0 ? startDataIndex : 0, dataCount, arrayNum, timeSampleCount, doubleData);
                        if (startDataIndex >= 0) {
                            writeData(tmpFileChannel, 0, dataCount, arrayNum, timeSampleCount, doubleData);
                        } else {
                            writeData(tmpFileChannel, -startDataIndex, dataCount, arrayNum, timeSampleCount, doubleData);
                        }
                        break;
                }

                tmpFileChannel.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        close();
        File file = new File(filePath);
        file.delete();
        tmpFile.renameTo(file);
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, byte[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            byte[] srcData = new byte[dataCount * timeSampleCount * arrayNum];
            buffer.get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, byte[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            byte[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new byte[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, short[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            short[] srcData = new short[dataCount * timeSampleCount * arrayNum];
            buffer.asShortBuffer().get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.asShortBuffer().get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, short[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asShortBuffer().get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            short[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new short[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asShortBuffer().get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, int[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            int[] srcData = new int[dataCount * timeSampleCount * arrayNum];
            buffer.asIntBuffer().get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.asIntBuffer().get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, int[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asIntBuffer().get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_INT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new int[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asIntBuffer().get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, long[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            long[] srcData = new long[dataCount * timeSampleCount * arrayNum];
            buffer.asLongBuffer().get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.asLongBuffer().get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, long[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asLongBuffer().get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            long[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new long[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asLongBuffer().get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, float[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            float[] srcData = new float[dataCount * timeSampleCount * arrayNum];
            buffer.asFloatBuffer().get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.asFloatBuffer().get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, float[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asFloatBuffer().get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            float[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new float[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asFloatBuffer().get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, double[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            double[] srcData = new double[dataCount * timeSampleCount * arrayNum];
            buffer.asDoubleBuffer().get(srcData);

            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                for (int j = 0; j < arrayNum; j++) {
                    System.arraycopy(srcData, position, data[i][j], 0, timeSampleCount);
                    position += timeSampleCount;
                }
            }

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum;
            int bufferSize = dataTypeSize * dataCount * timeSampleCount * arrayNum;

            fileChannel.position(offset);
            ByteBuffer buffer = getInitBuffer(bufferSize);
            fileChannel.read(buffer);
            buffer.rewind();
            buffer.asDoubleBuffer().get(data);

            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, double[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            int offset = 0;
            for (int i = 0; i < dataCount; i++) {
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asDoubleBuffer().get(data[i]);

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return 0;
        }
    }

    public int readData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();

            byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
            int bufferSize = dataTypeSize * timeSampleCount;

            //逐深度点读对应探头的数据
            ByteBuffer buffer = null;
            double[] desData = null;
            int offset = 0;
            int position = 0;
            for (int i = 0; i < dataCount; i++) {
                desData = new double[timeSampleCount];
                buffer = getInitBuffer(bufferSize);
                offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                        * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;

                fileChannel.position(offset);
                fileChannel.read(buffer);
                buffer.rewind();
                buffer.asDoubleBuffer().get(desData);

                System.arraycopy(desData, 0, data, position, timeSampleCount);
                position += timeSampleCount;

                ++startDataIndex;
            }
            close();
            return dataCount;
        } catch (IOException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, byte[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, byte[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        byte[] desData = new byte[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.put(desData, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, byte[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, byte[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, byte[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = null;
        for (int i = 0; i < dataCount; i++) {
            buffer = getInitBuffer(bufferSize);
            buffer.put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, byte[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, byte[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount;

        byte[] desData = null;
        ByteBuffer buffer = null;
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new byte[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer = getInitBuffer(bufferSize);
            buffer.put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, short[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, short[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        short[] desData = new short[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.asShortBuffer().put(desData, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, short[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asShortBuffer().put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, short[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, short[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount;

        short[] desData = null;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new short[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer.rewind();
            buffer.asShortBuffer().put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, short[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, short[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_SHORT);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = getInitBuffer(bufferSize);
        for (int i = 0; i < dataCount; i++) {
            buffer.rewind();
            buffer.asShortBuffer().put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, int[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, int[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        int[] desData = new int[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.asIntBuffer().put(desData, 0, dataCount);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, int[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asIntBuffer().put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, int[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, int[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount;

        int[] desData = null;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new int[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer.rewind();
            buffer.asIntBuffer().put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, int[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, int[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_INT);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = getInitBuffer(bufferSize);
        for (int i = 0; i < dataCount; i++) {
            buffer.rewind();
            buffer.asIntBuffer().put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, long[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, long[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        long[] desData = new long[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.asLongBuffer().put(desData, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, long[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asLongBuffer().put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, long[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, long[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount;

        long[] desData = null;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new long[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer.rewind();
            buffer.asLongBuffer().put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, long[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, long[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_LONG);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = getInitBuffer(bufferSize);
        for (int i = 0; i < dataCount; i++) {
            buffer.rewind();
            buffer.asLongBuffer().put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, float[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, float[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        float[] desData = new float[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.asFloatBuffer().put(desData, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, float[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asFloatBuffer().put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, float[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, float[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount;

        float[] desData = null;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new float[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer.rewind();
            buffer.asFloatBuffer().put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, float[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, float[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_FLOAT);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = getInitBuffer(bufferSize);
        for (int i = 0; i < dataCount; i++) {
            buffer.rewind();
            buffer.asFloatBuffer().put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, double[][][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, double[][][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        double[] desData = new double[dataCount * timeSampleCount * arrayNum];
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            for (int j = 0; j < arrayNum; j++) {
                System.arraycopy(data[i][j], 0, desData, position, timeSampleCount);
                position += timeSampleCount;
            }
        }
        buffer.asDoubleBuffer().put(desData, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayNum,
            int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayNum, int timeSampleCount, double[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * timeSampleCount * dataCount * arrayNum;
        ByteBuffer buffer = getInitBuffer(bufferSize);

        buffer.asDoubleBuffer().put(data, 0, dataCount * timeSampleCount * arrayNum);
        buffer.position(buffer.limit());

        int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                * dataTypeSize * timeSampleCount * arrayNum;
        return writeData(channel, offset, buffer);
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, double[] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, double[] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_BYTE);
        int bufferSize = dataTypeSize * timeSampleCount;

        double[] desData = null;
        ByteBuffer buffer = getInitBuffer(bufferSize);
        int position = 0;
        for (int i = 0; i < dataCount; i++) {
            desData = new double[timeSampleCount];
            System.arraycopy(data, position, desData, 0, timeSampleCount);
            position += timeSampleCount;

            buffer.rewind();
            buffer.asDoubleBuffer().put(desData, 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }

    public boolean writeData(int startDataIndex, int dataCount, int arrayIndex, int arrayNum,
            int timeSampleCount, double[][] data) {
        try {
            fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
            boolean retCode = writeData(fileChannel, startDataIndex, dataCount, arrayIndex,
                    arrayNum, timeSampleCount, data);
            close();
            return retCode;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Curve3DRwWrapper.class.getName()).log(Level.SEVERE, null, ex);
            close();
            return false;
        }
    }

    private boolean writeData(FileChannel channel, int startDataIndex, int dataCount,
            int arrayIndex, int arrayNum, int timeSampleCount, double[][] data) {
        byte dataTypeSize = Global.sizeof(Global.DATA_DOUBLE);
        int bufferSize = dataTypeSize * timeSampleCount;

        ByteBuffer buffer = getInitBuffer(bufferSize);
        for (int i = 0; i < dataCount; i++) {
            buffer.rewind();
            buffer.asDoubleBuffer().put(data[i], 0, timeSampleCount);
            buffer.position(buffer.limit());

            //逐深度点写对应探头的数据
            int offset = FILE_HEAD_EMPTY_BLOCK_SIZE + startDataIndex
                    * dataTypeSize * timeSampleCount * arrayNum + arrayIndex * timeSampleCount * dataTypeSize;
            boolean retCode = writeData(channel, offset, buffer);
            if (!retCode) {
                return false;
            }
            ++startDataIndex;
        }
        return true;
    }
}
