package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.Curve2DBean;
import com.cif.domain.DocBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.DocService;
import com.cif.service.WellService;
import com.cif.utils.PathUtil;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.DataTypeSwitch;
import com.cif.utils.util.FileDeal;
import com.cif.utils.util.GetJson;
import com.cif.utils.wrapper.Curve2DRwWrapper;
import com.cif.utils.wrapper.DocRwWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value="文档接口模块",tags = "文档接口模块")
public class DocController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Autowired
    DocService docService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取文档信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取文档信息")
    @RequestMapping(value = "/doc",method = RequestMethod.GET)
    public ResponseEntity getDoc(int id){

        Map<String,Object> data = new HashMap<>();

        DocBean doc = docService.getDoc(id);

        if(doc != null){

            data.put("data",doc);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("data",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id、ownerid获取所有的文档信息
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id、ownerid获取所有的文档信息")
    @RequestMapping(value = "/doc/all",method = RequestMethod.GET)
    public ResponseEntity getAllDocs(int categoryID, Integer owner){

        Map<String,Object> data = new HashMap<>();

        DocBean[] docs = docService.getAllDocs(categoryID);

        if(docs != null && docs.length > 0){

            data.put("data",docs);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条文档记录
     * @param categoryID
     * @param name
     * @param owner
     * @param file
     * @return
     */
    @ApiOperation(value = "新增一条文档记录")
    @RequestMapping(value = "/doc",method = RequestMethod.POST)
    public ResponseEntity addDoc(int categoryID,String name,int owner,String file){

        DocBean db = new DocBean();

        db.setCategoryID(categoryID);

        db.setName(name);

        db.setOwner(owner);

        db.setFile(file);

        Map<String,Object> data = new HashMap<>();

        int index = docService.addDoc(db);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",db.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条文档记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条文档记录")
    @RequestMapping(value = "/doc",method = RequestMethod.DELETE)
    public ResponseEntity deleteDoc(String id){

        Map<String,Object> data = new HashMap<>();

        /*删除记录之前 -- 根据id获取该曲线的信息*/
        DocBean doc = docService.getDoc(Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(doc.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + doc.getFile();

        int index = docService.deleteDoc(Integer.parseInt(id));

        /*判断记录是否删除成功*/
        if(index > 0){

            /*删除记录的同时，将对应路径下的文件删除掉*/
            File file = new File(filepath);

            if(file.isFile() && file.exists()){
                file.delete();
            }

        }

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 更新一条文档记录
     * @param request
     */
    @ApiOperation(value = "更新一条文档记录")
    @RequestMapping(value = "/doc",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateDoc(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        DocBean cb = JSONObject.parseObject(jsonObject.toJSONString(),DocBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = docService.updateDoc(cb);

        data.put("data",index);

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
    @RequestMapping(value = "/doc/upload",method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam(value="categoryID",required = true) String categoryID,@RequestParam(value="file",required = true) MultipartFile file,HttpServletRequest request){

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
            String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + "doc" + File.separator;

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

            //文件上传完成后更新该文档记录的大小

            DocBean cb = docService.getDocByFileName(file.getOriginalFilename(),Integer.parseInt(categoryID));

            cb.setSize(file.getSize());

            docService.updateDoc(cb);

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
    @RequestMapping(value = "/doc/readDoc",method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(@RequestParam(value="fileName",required = true) String fileName,@RequestParam(value="categoryID",required = true) String categoryID) throws FileNotFoundException {

        /*根据井次id获取井id*/
        int wellId = getWellId(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + "doc" + File.separator;


        File dir = new File(PathUtil.getFullPathNoEndSeparator(filepath));

        //如果没有目录则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }


        File file = new File(filepath+fileName);

        //创建该文件
        if (file.exists()) {
            return FileDeal.export(file);
        }

        return null;
    }

    /**
     * 根据文档id读取文档文件的数据
     * @return
     */
    @ApiOperation(value = "根据文档id读取文档文件的数据")
    @RequestMapping(value = "/doc/data",method = RequestMethod.GET)
    public ResponseEntity getData(String id,String offset,String byteSize,String type){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取文档信息*/
        DocBean doc = docService.getDoc(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据文档id获取井id*/
        int wellId = getWellId(doc.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + doc.getFile();

        /*初始化时，判断filepath是否存在，不存在，则会创建*/
        DocRwWrapper wrapper = new DocRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        int num = 0;

        byte[] floatData = new byte[byteSize.equals("") || byteSize == null ? null : Integer.parseInt(byteSize)];

        num = wrapper.readData(DataTypeSwitch.toInt(offset),DataTypeSwitch.toInt(byteSize),floatData);

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();
        result.put("num",num);
        result.put("dataArr",floatData);

        data.put("data",result);

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
