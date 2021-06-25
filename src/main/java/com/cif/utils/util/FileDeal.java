package com.cif.utils.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;

public class FileDeal {

    /**
     * 删除单个文件
     * @param pathName  删除文件路径名
     * @return
     */
    public static boolean deleteFiles(String pathName){
        boolean flag = false;
        //根据路径创建文件对象
        File file = new File(pathName);
        //路径是个文件且不为空时删除文件
        if(file.isFile()&&file.exists()){
            flag = file.delete();
        }
        //删除失败时，返回false
        return flag;
    }

    /**
     * 删除目录本身以及目录下的所有文件及文件夹
     * @param pathName  目录名
     * @return
     */
    public static boolean deleteDiretory(String pathName){

        boolean flag = false;

        //根据路径创建文件对象
        File directory = new File(pathName);

        //如果路径是一个目录且不为空时，删除目录
        if(directory.isDirectory()&&directory.exists()){

            //获取目录下的所有的目录和文件，放入数组files中
            File[] files = directory.listFiles();

            //遍历目录下的所有的文件和目录
            for(int i= 0;i<files.length;i++){

                //如果目录下是文件时，调用deleteFiles（）方法，删除单个文件
                if (files[i].isFile()){

                    flag = deleteFiles(files[i].getAbsolutePath());

                }//如果目录下是目录时，调用自身deleteDirectory()，形成递归调用
                else{

                    flag = deleteDiretory(files[i].getAbsolutePath());

                }
            }

            //删除目录本身，如果想要保留目录只删除文件，此句可以不要
            flag = directory.delete();

        }

        //删除成功时返回true，失败时返回false
        return flag;

    }

    /**
     * 删除目录下的所有文件及文件夹
     * @param pathName  目录名
     * @return
     */
    public static boolean deleteAllFile(String pathName){

        boolean flag = false;

        //根据路径创建文件对象
        File directory = new File(pathName);

        //如果路径是一个目录且不为空时，删除目录
        if(directory.isDirectory()&&directory.exists()){

            //获取目录下的所有的目录和文件，放入数组files中
            File[] files = directory.listFiles();

            //遍历目录下的所有的文件和目录
            for(int i= 0;i<files.length;i++){

                //如果目录下是文件时，调用deleteFiles（）方法，删除单个文件
                if (files[i].isFile()){

                    flag = deleteFiles(files[i].getAbsolutePath());

                }//如果目录下是目录时，调用自身deleteDirectory()，形成递归调用
                else{

                    flag = deleteDiretory(files[i].getAbsolutePath());

                }
            }

        }

        //删除成功时返回true，失败时返回false
        return flag;

    }

    /**
     * 返回文件流
     * @param file
     * @return
     */
    public static ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

}
