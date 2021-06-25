package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.WellBean;
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
@Api(value = "井接口模块",tags = "井接口模块")
public class WellController {

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取井信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取井信息")
    @RequestMapping(value = "/well",method = RequestMethod.GET)
    public ResponseEntity getWell(int id){

        Map<String,Object> data = new HashMap<>();

        WellBean well = wellService.getWell(id);

        if(well != null){

            data.put("data",well);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据工区id和owner获取所有的井信息
     * @param workSpaceID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据工区id和owner获取所有的井信息")
    @RequestMapping(value = "/well/all",method = RequestMethod.GET)
    public ResponseEntity getAllWells(String workSpaceID,String owner){

        Map<String,Object> data = new HashMap<>();

        WellBean[] well = wellService.getAllWells(workSpaceID.equals("") || workSpaceID == null ? null : Integer.parseInt(workSpaceID));

        if(well != null && well.length > 0 ){

            data.put("data",well);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条井记录
     * @param request
     */
    @ApiOperation(value = "新增一条井记录")
    @RequestMapping(value = "/well",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addWell(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        WellBean cb = JSONObject.parseObject(jsonObject.toJSONString(),WellBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = wellService.addWell(cb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",cb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条井记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条井记录")
    @RequestMapping(value = "/well",method = RequestMethod.DELETE)
    public ResponseEntity deleteWell(int id){

        Map<String,Object> data = new HashMap<>();

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(id);

        /*通过井id、工区id，拼接 文件夹 的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + id;

        /*删除井的记录*/
        int index = wellService.deleteWell(id);

        /*判断记录是否删除成功*/
        if(index > 0){

            /*删除记录成功，则删除对应的文件夹*/
            FileDeal.deleteDiretory(filepath);

        }

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 更新一条井记录
     * @param request
     */
    @ApiOperation(value = "更新一条井记录")
    @RequestMapping(value = "/well",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateWell(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        WellBean cb = JSONObject.parseObject(jsonObject.toJSONString(),WellBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = wellService.updateWell(cb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据名称、工区id、owner查找对应的记录信息
     * @param name
     * @param workSpaceID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据名称、工区id、owner查找对应的记录信息")
    @RequestMapping(value = "/well/find",method = RequestMethod.GET)
    public ResponseEntity findWell(String name,int workSpaceID,Integer owner){

        Map<String,Object> data = new HashMap<>();

        WellBean well = wellService.findWell(name,workSpaceID);

        if(well != null){

            data.put("data",well);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

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
