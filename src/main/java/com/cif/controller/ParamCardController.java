package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.DocBean;
import com.cif.domain.ParamCardBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.ParamCardService;
import com.cif.service.WellService;
import com.cif.utils.PathUtil;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.FileDeal;
import com.cif.utils.util.GetJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value="参数卡片接口模块",tags = "参数卡片接口模块")
public class ParamCardController {

    @Autowired
    ParamCardService paramCardService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取参数卡片信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取参数卡片信息")
    @RequestMapping(value = "/pc",method = RequestMethod.GET)
    public ResponseEntity getParamCard(int id){

        Map<String,Object> data = new HashMap<>();

        ParamCardBean paramCardBean = paramCardService.getParamCard(id);

        if(paramCardBean != null){

            data.put("data",paramCardBean);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("data",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id、owner获取所有的参数卡片数据
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id、owner获取所有的参数卡片数据")
    @RequestMapping(value = "/pc/all",method = RequestMethod.GET)
    public ResponseEntity getAllParamCards(int categoryID,Integer owner){

        Map<String,Object> data = new HashMap<>();

        ParamCardBean[] paramCardBeans = paramCardService.getAllParamCards(categoryID);

        if(paramCardBeans != null && paramCardBeans.length > 0){

            data.put("data",paramCardBeans);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条参数卡片记录
     * @param request
     */
    @ApiOperation(value = "新增一条参数卡片记录")
    @RequestMapping(value = "/pc",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addParamCard(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        ParamCardBean pcb = JSONObject.parseObject(jsonObject.toJSONString(),ParamCardBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = paramCardService.addParamCard(pcb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",pcb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 上传文件到指定路径
     * @param categoryID
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "上传文件到指定路径")
    @RequestMapping(value = "/pc/upload",method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam(value="category",required = true) String categoryID, @RequestParam(value="file",required = true) MultipartFile file, HttpServletRequest request){

        Map<String,Object> data = new HashMap<String,Object>();

        /*根据井次id获取井id*/
        int wellId = getWellId(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        InputStream in = null;
        OutputStream out = null;
        try {

            in = file.getInputStream();

            /*服务器存储路径 通过井id、工区id，拼接文件的路径*/
            String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + "paramCard" + File.separator;

            //文件夹路径
            File fileDir = new File(filepath);

            //判断文件夹路径是否存在，不存在，则创建该路径
            if(!fileDir.exists()){

                fileDir.mkdir();

            }

            //创建要上传的路径
            File targetFile = new File(filepath+file.getOriginalFilename());

            out = new FileOutputStream(targetFile);

            FileCopyUtils.copy(in,out);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * 读取服务器文件，并返回文件流
     * @param fileName
     * @param categoryID
     * @return
     * @throws FileNotFoundException
     */
    @ApiOperation(value = "读取服务器文件，并返回文件流")
    @RequestMapping(value = "/pc/readDoc",method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(@RequestParam(value="fileName",required = true) String fileName, @RequestParam(value="categoryID",required = true) String categoryID) throws FileNotFoundException {

        /*根据井次id获取井id*/
        int wellId = getWellId(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + "paramCard" + File.separator;


        File dir = new File(PathUtil.getFullPathNoEndSeparator(filepath));

        //如果没有目录则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //根绝卡片名称查询卡片文件的名称
        ParamCardBean paramCardBean = paramCardService.getParamCardByName(fileName);

        File file = new File(filepath+paramCardBean.getFile());

        //创建该文件
        if (file.exists()) {
            return FileDeal.export(file);
        }

        return null;
    }

    /**
     * 删除一条参数卡片记录
     * @param id
     */
    @ApiOperation(value = "删除一条参数卡片记录")
    @RequestMapping(value = "/pc",method = RequestMethod.DELETE)
    public ResponseEntity deleteParamCard(int id){

        Map<String,Object> data = new HashMap<>();

        int index = paramCardService.deleteParamCard(id);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 更新一条参数卡片记录
     */
    @ApiOperation(value = "更新一条参数卡片记录")
    @RequestMapping(value = "/pc",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateParamCard(@RequestBody ParamCardBean pc){

//        //获取到JSONObject
//        JSONObject jsonObject = GetJson.getJSONParam(request);
//
//        //JSON对象转换成WorkSpaceBean实体
//        ParamCardBean pc = JSONObject.parseObject(jsonObject.toJSONString(),ParamCardBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = paramCardService.updateParamCard(pc);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据井次id获取记录中的井id
     * @param id
     * @return
     */
    public int getWellId(int id){

        CategoryBean cate = categoryService.getCategory(id);
        return cate.getWellID();

    }

    /**
     * 根据井id获取工区id
     * @param id
     * @return
     */
    public int getWorkSpaceId(int id){

        WellBean well = wellService.getWell(id);
        return well.getWorkSpaceID();

    }
}
