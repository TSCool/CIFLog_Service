package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.DatasourceBean;
import com.cif.service.DatasourceService;
import com.cif.utils.util.GetJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 数据源控制台
 *
 * @author liutianshuo
 * @Date 2022/8/25
 * @Version 1.0
 **/
@RestController
@Api(value="数据源接口模块",tags = "数据源接口模块")
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    /**
     * 根据id获取数据源信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取数据源信息")
    @RequestMapping(value = "/ds",method = RequestMethod.GET)
    public ResponseEntity getDataSource(int id){

        Map<String,Object> data = new HashMap<>();

        DatasourceBean datasourceBean = datasourceService.getDataSource(id);

        if(datasourceBean != null){

            data.put("data",datasourceBean);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 查询所有的数据源
     * @return
     */
    @ApiOperation(value = "查询所有的数据源")
    @RequestMapping(value = "/ds/all",method = RequestMethod.GET)
    public ResponseEntity getAllWorkSpaces(){

        Map<String,Object> data = new HashMap<>();

        //查询的所有数据源
        DatasourceBean[] workSpace = datasourceService.getAll();

        if(workSpace != null && workSpace.length > 0){

            data.put("data", workSpace);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一个数据源
     * @param request
     * @return
     */
    @ApiOperation(value = "新增数据源")
    @RequestMapping(value = "/ds",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addDatasource(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成DatasourceBean实体
        DatasourceBean das = JSONObject.parseObject(jsonObject.toJSONString(),DatasourceBean.class);

        Map<String,Object> data = new HashMap<>();

        //调用service层新增方法实现
        int index = datasourceService.addDataSource(das);

        //如果新增成功，将ID返回
        if(index > 0){

            data.put("data",das.getId());

        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条数据源记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条数据源记录")
    @RequestMapping(value = "/ds",method = RequestMethod.DELETE)
    public ResponseEntity deleteDatasource(int id){

        Map<String,Object> data = new HashMap<>();

        int index = datasourceService.deleteDataSource(id);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 更新一条数据源记录信息
     * @param request
     */
    @ApiOperation(value = "更新数据源记录")
    @RequestMapping(value = "/ds",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateDatasource(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成DatasourceBean实体
        DatasourceBean wsb = JSONObject.parseObject(jsonObject.toJSONString(),DatasourceBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = datasourceService.updateDataSource(wsb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

}
