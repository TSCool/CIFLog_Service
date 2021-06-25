package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.Curve1DBean;
import com.cif.domain.Curve3DBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.Curve3DService;
import com.cif.service.WellService;
import com.cif.utils.util.DataTypeSwitch;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.GetJson;
import com.cif.utils.wrapper.Curve1DRwWrapper;
import com.cif.utils.wrapper.Curve3DRwWrapper;
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
@RequestMapping("curve")
@Api(value = "三维曲线接口模块",tags = "三维曲线接口模块")
public class Curve3DController {

    @Autowired
    Curve3DService curve3DService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取3维曲线信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取3维曲线信息")
    @RequestMapping(value = "/3d",method = RequestMethod.GET)
    public ResponseEntity getCurve3DById(int id){

        Map<String,Object> data = new HashMap<>();

        Curve3DBean curve3D = curve3DService.getCurve3D(id);

        if(curve3D != null){

            data.put("data",curve3D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id和owner查询所有的三维曲线信息
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id和owner查询所有的三维曲线信息")
    @RequestMapping(value = "/3d/all",method = RequestMethod.GET)
    public ResponseEntity getAllCurve3Ds(String categoryID,String owner){

        Map<String,Object> data = new HashMap<>();

        Curve3DBean[] curve3D = curve3DService.getAllCurve3Ds(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        if(curve3D != null && curve3D.length > 0){

            data.put("data",curve3D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条三维曲线记录
     * @param request
     */
    @ApiOperation(value = "新增一条三维曲线记录")
    @RequestMapping(value = "/3d",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addCurve3D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve3DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve3DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve3DService.addCurve3D(cb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",cb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条三维曲线记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条三维曲线记录")
    @RequestMapping(value = "/3d",method = RequestMethod.DELETE)
    public ResponseEntity deleteCurve3D(int id){

        Map<String,Object> data = new HashMap<>();

        /*删除记录之前 -- 根据id获取该曲线的信息*/
        Curve3DBean curve3D = curve3DService.getCurve3D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve3D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve3D.getFile();

        /*删除该曲线记录*/
        int index = curve3DService.deleteCurve3D(id);

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
     * 更新一条三维曲线记录
     * @param request
     */
    @ApiOperation(value = "更新三维曲线记录")
    @RequestMapping(value = "/3d",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateCurve3D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve3DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve3DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve3DService.updateCurve3D(cb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据三维曲线id读取三维曲线文件的数据
     * @return
     */
    @ApiOperation(value = "根据三维曲线id读取三维曲线文件的数据,")
    @RequestMapping(value = "/3d/data",method = RequestMethod.GET)
    public ResponseEntity readData(String id,String startDataIndex,String dataCount,String arrayNum,
                                   String timeSampleCount,String type){
        Map<String,Object> data = new HashMap<>();

        /*根据id获取3维曲线信息*/
        Curve3DBean curve3D = curve3DService.getCurve3D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve3D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve3D.getFile();

        Curve3DRwWrapper wrapper = new Curve3DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        int num = 0;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        /*根据类型参数类型，判断数组类型*/
        switch(type){
            case "float[]":

                float[] floatData = new float[dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),
                        DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleCount),floatData);

                result.put("dataArr",floatData.length > 0 ? floatData : null);

            case "short[]":
                short[] shortData = new short[dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),
                        DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleCount),shortData);

                result.put("dataArr",shortData.length > 0 ? shortData : null);

            default:
                System.out.println("default");break;
        }

        result.put("num",num);

        if(result.get("dataArr") != null){
            data.put("data",result);
            data.put("status",200);
        }else{
            data.put("data","暂无数据");
            data.put("status",200);
        }
        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据三维曲线id读取三维曲线文件的数据2
     * @return
     */
    @ApiOperation(value = "根据三维曲线id读取三维曲线文件的数据2")
    @RequestMapping(value = "/3d/data/arrayIndex",method = RequestMethod.GET)
    public ResponseEntity readDataArrayIndex(String id,String startDataIndex,String dataCount,String arrayIndex,String arrayNum,
                                   String timeSampleCount,String type){
        Map<String,Object> data = new HashMap<>();

        /*根据id获取3维曲线信息*/
        Curve3DBean curve3D = curve3DService.getCurve3D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve3D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve3D.getFile();

        Curve3DRwWrapper wrapper = new Curve3DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        int num = 0;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        /*根据类型参数类型，判断数组类型*/
        switch(type){

            case "float[]":

                float[] floatData = new float[(dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)) * Integer.parseInt(timeSampleCount)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),DataTypeSwitch.toInt(arrayIndex),
                        DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleCount),floatData);

                result.put("dataArr",floatData.length > 0 ? floatData : null);

            case "short[]":

                short[] shortData = new short[(dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)) * Integer.parseInt(timeSampleCount)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),DataTypeSwitch.toInt(arrayIndex),
                        DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleCount),shortData);

                result.put("dataArr",shortData.length > 0 ? shortData : null);

            default:
                System.out.println("default");break;
        }

        result.put("num",num);

        if(result.get("dataArr") != null){
            data.put("data",result);
            data.put("status",200);
        }else{
            data.put("data","暂无数据");
            data.put("status",200);
        }
        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据起始点修改三维曲线文件的数据
     * @return
     */
    @ApiOperation(value = "根据起始点修改三维曲线文件的数据")
    @RequestMapping(value = "/3d/changeDepthRange",method = RequestMethod.GET)
    public ResponseEntity changeDepthRange(int id,byte dataType,int startDataIndex,int endDataIndex, int arrayNum, int timeSampleCount,boolean preserved){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取3维曲线信息*/
        Curve3DBean curve3D = curve3DService.getCurve3D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve3D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve3D.getFile();

        Curve3DRwWrapper wrapper = new Curve3DRwWrapper(filepath);

        try {
            wrapper.changeDepthRange(dataType,startDataIndex,endDataIndex,arrayNum,timeSampleCount,preserved);
            data.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            data.put("status",500);
        }

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 写入三维曲线数据
     * @param dataCount
     * @return
     */
    @ApiOperation(value = "写入三维曲线数据")
    @RequestMapping(value = "/3d/writedata",method = RequestMethod.POST)
    public ResponseEntity writeData(String id, String startDataIndex, String dataCount,String arrayIndex,String arrayNum,String timeSampleNum, String type,String allData){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取2维曲线信息*/
        Curve3DBean curve3D = curve3DService.getCurve3D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve3D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve3D.getFile();

        Curve3DRwWrapper wrapper = new Curve3DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        boolean isFlag = false;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        //将字符串转换成对应的数组
        allData = allData.substring(1,(allData.length()-1));
        String[] strData = allData.split(",");

        /*根据类型参数类型，判断数组类型*/
        switch(type){
            case "float[]":

                float[] floatData = new float[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){
                    floatData[i] = Float.parseFloat(strData[i]);
                }

                if(arrayIndex == null || arrayIndex.equals("")){
                    isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleNum), floatData);
                }else{
                    isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(arrayIndex), DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleNum), floatData);
                }


                result.put("dataArr",floatData.length > 0 ? floatData : null);

                break; //可选

            case "short[]":

                short[] shortData = new short[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){
                    shortData[i] = Short.parseShort(strData[i]);
                }

                if(arrayIndex == null || arrayIndex.equals("")){
                    isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleNum), shortData);
                }else{
                    isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(arrayIndex), DataTypeSwitch.toInt(arrayNum), DataTypeSwitch.toInt(timeSampleNum), shortData);
                }

                result.put("dataArr",shortData.length > 0 ? shortData : null);

                break; //可选

            default:
                System.out.println("default");break;
        }

        result.put("isFlag",isFlag);

        if(result.get("dataArr") != null){
            data.put("data",result);
            data.put("status",200);
        }else{
            data.put("status",200);
        }

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
