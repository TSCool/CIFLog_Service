package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.Curve1DBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.WellService;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.FileDeal;
import com.cif.utils.util.GetJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "井次接口模块",tags = "井次接口模块")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id查询井次信息
     * @param id
     * @api {GET}
     * @apiParamExample {json}
     * {}
     */
    @ApiOperation(value = "根据id查询井次信息")
    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ResponseEntity getCategoryById(int id){

        Map<String,Object> data = new HashMap<>();

        CategoryBean cate = categoryService.getCategory(id);

        if(cate != null){

            data.put("data",cate);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("data",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井id和owner查询所有的井次信息
     * @param wellID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井id和owner查询所有的井次信息")
    @RequestMapping(value = "/category/all",method = RequestMethod.GET)
    public ResponseEntity getAllCategorys(String wellID, String owner){

        Map<String,Object> data = new HashMap<>();

        CategoryBean[] cates = categoryService.getAllCategorys(wellID.equals("") || wellID == null ? null : Integer.parseInt(wellID));

        if(cates != null && cates.length > 0){

            data.put("data",cates);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 新增井次信息
     * @param request
     * @return
     */
    @ApiOperation(value = "新增井次信息")
    @RequestMapping(value = "/category",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addCategory(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        CategoryBean cb = JSONObject.parseObject(jsonObject.toJSONString(),CategoryBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = categoryService.addCategory(cb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",cb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条井次信息记录
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除一条井次信息记录")
    @RequestMapping(value = "/category",method = RequestMethod.DELETE)
    public ResponseEntity deleteCategory(int id){

        Map<String,Object> data = new HashMap<>();

        /*根据井次id获取井id*/
        int wellId = getWellId(id);

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接 文件夹 的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId;

        /*删除该井次记录*/
        int index = categoryService.deleteCategory(id);

        /*判断记录是否删除成功*/
        if(index > 0){

            /*删除记录成功，则删除对应的文件夹下所有的文件*/
            FileDeal.deleteAllFile(filepath);

        }

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 更新一条井次信息记录
     * @param request
     * @return
     */
    @ApiOperation(value = "更新一条井次信息记录")
    @RequestMapping(value = "/category",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateCategory(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成CategoryBean实体
        CategoryBean cb = JSONObject.parseObject(jsonObject.toJSONString(),CategoryBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = categoryService.updateCategory(cb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 稍后用不到可以删掉了
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
