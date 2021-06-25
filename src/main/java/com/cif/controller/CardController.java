package com.cif.controller;

import com.cif.utils.base.CardAssistance;
import com.cif.utils.base.Global;
import com.cif.utils.constant.FilePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "卡片接口模块", tags = "卡片接口模块")
public class CardController {

    @Value("${file.path}")
    private String CIFLogFiles;
    /**
     * 文件是否存在
     * @param fileName
     * @return
     */
    @ApiOperation(value = "文件是否存在")
    @RequestMapping(value = "/card/isExist" , method = RequestMethod.GET)
    public ResponseEntity fileExists(String fileName,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*拼接文件夹路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + fileName;

        File cardFile = new File(cardFilePath);

        data.put("data",cardFile.isFile() && cardFile.exists());

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据名称获取文件
     * @param fileName
     * @return
     */
    @ApiOperation(value = "根据名称获取文件")
    @RequestMapping(value = "/card/file" , method = RequestMethod.GET)
    public ResponseEntity getFile(String fileName,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*拼接文件夹路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + fileName;
        File cardFile = new File(cardFilePath);
        if (cardFile.isFile() && cardFile.exists()) {
            data.put("data",cardFile);
            data.put("status",200);
        }else{
            data.put("data","暂无该文件");
            data.put("status",200);
        }

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据 suffix 获取所有的文件名称
     * @param suffix
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据 suffix 获取所有的文件名称")
    @RequestMapping(value = "/card/all/names/by" , method = RequestMethod.GET)
    public ResponseEntity getFileNames(String suffix,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*拼接文件夹路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards";

        suffix = suffix.trim();

        File dirFile = new File(cardFilePath);

        if (!dirFile.exists() || !dirFile.isDirectory()) {

            data.put("data",null);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }

        File[] files = dirFile.listFiles();

        if (files == null || files.length == 0) {

            data.put("data",null);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }

        ArrayList<String> cardNames = new ArrayList<String>();

        String fn;

        File f;

        for (File file : files) {

            f = file;

            if (f.isFile() && !f.isHidden()) {

                fn = file.getName().trim();

                if (fn.endsWith(suffix)) {

                    cardNames.add(fn);

                }

            }

        }

        data.put("data",cardNames.toArray(new String[0]));

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 获取所有的文件名称
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "获取所有的文件名称")
    @RequestMapping(value = "/card/all/names",method = RequestMethod.GET)
    public ResponseEntity getFileNames(String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*拼接文件夹路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards";

        File file = new File(cardFilePath);

        if (!file.exists() || !file.isDirectory()) {

            data.put("data",null);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }

        File[] files = file.listFiles();

        if (files == null || files.length == 0) {

            data.put("data",null);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }

        ArrayList<String> cardNames = new ArrayList<String>();

        String fn;

        File f;

        for (File file1 : files) {

            f = file1;

            if (f.isFile() && !f.isHidden()) {

                fn = file1.getName().trim();

                Collection<? extends CardAssistance> cardAssistances = Global.getCardAssistances();

                for (CardAssistance assistance : cardAssistances) {

                    if (fn.endsWith(assistance.getSuffix())) {

                        cardNames.add(fn);

                        break;

                    }

                }

            }

        }

        data.put("data",cardNames.toArray(new String[0]));

        data.put("status",200);

        return  new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据文件名称删除文件
     * @param fileName
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据文件名称删除文件")
    @RequestMapping(value = "/card/delete" , method = RequestMethod.DELETE)
    public ResponseEntity deleteFile(String fileName,String workspaceId){
        Map<String,Object> data = new HashMap<>();

        /*拼接文件路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + fileName;

        File cardFile = new File(cardFilePath);

        Boolean isFlag = true;

        if (!cardFile.exists()) {

            isFlag = false;

        }else{

            isFlag = cardFile.delete();

        }

        data.put("data",isFlag);

        data.put("status",200);

        return new ResponseEntity(data, HttpStatus.OK);
    }

    /**
     * 根据文件名称删除文件 -- 方法
     * @param fileName
     * @param workspaceId
     * @return
     */
    public boolean deleteFileFunc(String fileName,String workspaceId){
        Map<String,Object> data = new HashMap<>();

        /*拼接文件路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + fileName;

        File cardFile = new File(cardFilePath);

        Boolean isFlag = true;

        if (!cardFile.exists()) {

            isFlag = false;

        }else{

            isFlag = cardFile.delete();

        }

        return isFlag;
    }

    /**
     * 根据文件名称数组，删除多个文件
     * @param fileNames
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据文件名称数组，删除多个文件")
    @RequestMapping(value = "/card/deletes" , method = RequestMethod.DELETE)
    public ResponseEntity deleteFiles(String fileNames,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        String[] fileArr = fileNames.substring(1,fileNames.length()-1).split(", ");

        boolean isFlag = true;

        for (String fileName : fileArr) {
            if (!deleteFileFunc(fileName,workspaceId)) {
                isFlag = false;
            }
        }

        data.put("data",isFlag);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 文件重新命名
     * @param fileName
     * @param newName
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "文件重新命名")
    @RequestMapping(value = "/card/rename" , method = RequestMethod.GET)
    public ResponseEntity renameFile(String fileName,String newName,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*拼接文件路径*/
        String cardFilePath = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + fileName;

        /*拼接文件新名称路径*/
        String cardFilePathNew = CIFLogFiles + workspaceId + File.separator + "Cards" + File.separator + newName;

        File cardFile = new File(cardFilePath);

        boolean isFlag = true;

        if (!cardFile.exists()) {

            isFlag = false;

        }else{

            isFlag = cardFile.renameTo(new File(cardFilePathNew));

        }

        data.put("data",isFlag);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);
    }

}
