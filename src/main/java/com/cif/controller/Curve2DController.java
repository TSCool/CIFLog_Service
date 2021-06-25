package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.Curve2DBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.Curve2DService;
import com.cif.service.WellService;
import com.cif.utils.util.DataTypeSwitch;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.GetJson;
import com.cif.utils.wrapper.Curve2DRwWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("curve")
@Api(value = "二维曲线接口模块",tags = "二维曲线接口模块")
public class Curve2DController {

    @Autowired
    Curve2DService curve2DService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取2维曲线信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取二维曲线信息")
    @RequestMapping(value = "/2d",method = RequestMethod.GET)
    public ResponseEntity getCurve2DById(int id){

        Map<String,Object> data = new HashMap<>();

        Curve2DBean curve2D = curve2DService.getCurve2D(id);

        if(curve2D != null){

            data.put("data",curve2D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id和owner查询所有的二维曲线信息
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id和owner查询所有的二维曲线信息")
    @RequestMapping(value = "/2d/all",method = RequestMethod.GET)
    public ResponseEntity getAllCurve2Ds(String categoryID,String owner){

        Map<String,Object> data = new HashMap<>();

        Curve2DBean[] curve2D = curve2DService.getAllCurve2Ds(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        if(curve2D != null && curve2D.length > 0){

            data.put("data",curve2D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条二维曲线记录
     * @param request
     */
    @ApiOperation(value = "新增一条二维曲线记录")
    @RequestMapping(value = "/2d",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addCurve2D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve2DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve2DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve2DService.addCurve2D(cb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",cb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条二维曲线记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条二维曲线记录")
    @RequestMapping(value = "/2d",method = RequestMethod.DELETE)
    public ResponseEntity deleteCurve2D(int id){

        Map<String,Object> data = new HashMap<>();

        /*删除记录之前 -- 根据id获取该曲线的信息*/
        Curve2DBean curve2D = curve2DService.getCurve2D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve2D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve2D.getFile();

        /*删除该曲线记录*/
        int index = curve2DService.deleteCurve2D(id);

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
     * 更新一条二维曲线记录
     * @param request
     */
    @ApiOperation(value = "更新二维曲线记录")
    @RequestMapping(value = "/2d",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateCurve2D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve2DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve2DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve2DService.updateCurve2D(cb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据二维曲线id读取二维曲线文件的数据
     * @param id
     * @param len
     * @param startDataIndex
     * @param dataCount
     * @param timeSampleCount
     *      * @param type
     *      * @return
     */
    @ApiOperation(value = "根据二维曲线id读取二维曲线文件的数据,")
    @RequestMapping(value = "/2d/data",method = RequestMethod.GET)
    public ResponseEntity readData(String id,String len,String startDataIndex,String dataCount,String timeSampleCount,String type){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取2维曲线信息*/
        Curve2DBean curve2D = curve2DService.getCurve2D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve2D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve2D.getFile();

        Curve2DRwWrapper wrapper = new Curve2DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        int num = 0;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        /*根据类型参数类型，判断数组类型*/
        switch(type){
            case "float[]":

                float[] floatData = new float[dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex),DataTypeSwitch.toInt(dataCount),DataTypeSwitch.toInt(timeSampleCount),floatData);

                result.put("dataArr",floatData.length > 0 ? floatData : null);

            case "short[]":

                short[] shortData = new short[len.equals("") || len == null ? null : Integer.parseInt(len)];

                num = wrapper.readData(DataTypeSwitch.toInt(startDataIndex),DataTypeSwitch.toInt(dataCount),DataTypeSwitch.toInt(timeSampleCount),shortData);

                result.put("dataArr",shortData.length > 0 ? shortData : null);

            default:
                System.out.println("default");break;
        }

        result.put("num",num);

        if(result.get("dataArr") != null){
            data.put("data",result);
            data.put("status",200);
        }else{
            data.put("status",200);
        }
        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据起始点修改二维曲线文件的数据
     * @param id
     * @param dataType
     * @param startDataIndex
     * @param endDataIndex
     * @param timeSampleCount
     * @param preserved
     * @return
     */
    @ApiOperation(value = "根据起始点修改二维曲线文件的数据")
    @RequestMapping(value = "/2d/changeDepthRange",method = RequestMethod.GET)
    public ResponseEntity changeDepthRange(int id,byte dataType,int startDataIndex,int endDataIndex,int timeSampleCount,boolean preserved){
        Map<String,Object> data = new HashMap<>();

        /*根据id获取2维曲线信息*/
        Curve2DBean curve2D = curve2DService.getCurve2D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve2D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve2D.getFile();

        Curve2DRwWrapper wrapper = new Curve2DRwWrapper(filepath);

        try {
            wrapper.changeDepthRange(dataType,startDataIndex,endDataIndex,timeSampleCount,preserved);
            data.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            data.put("status",500);
        }

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 写入二维曲线数据
     * @param sampleIndex
     * @param dataCount
     * @param timeSampleNum
     * @return
     */
    @ApiOperation(value = "写入二维曲线数据")
    @RequestMapping(value = "/2d/writedata",method = RequestMethod.POST)
    public ResponseEntity writeData(String id, String sampleIndex, String dataCount, String timeSampleNum, String type,String allData){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取2维曲线信息*/
        Curve2DBean curve2D = curve2DService.getCurve2D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve2D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve2D.getFile();

        Curve2DRwWrapper wrapper = new Curve2DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        boolean isFlag = false;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        //将字符串转换成对应的数组
        allData = allData.substring(1,(allData.length()-1));
        String[] strData = allData.split(",");

        System.out.println("查看二维数据类型"+type);

        /*根据类型参数类型，判断数组类型*/
        switch(type){
            case "float[]":

                float[] floatData = new float[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){
                    floatData[i] = Float.parseFloat(strData[i]);
                }

                isFlag = wrapper.writeData(DataTypeSwitch.toInt(sampleIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(timeSampleNum), floatData);

                result.put("dataArr",floatData.length > 0 ? floatData : null);

                break; //可选

            case "short[]":

                short[] shortData = new short[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){
                    shortData[i] = Short.parseShort(strData[i]);
                }

                isFlag = wrapper.writeData(DataTypeSwitch.toInt(sampleIndex), DataTypeSwitch.toInt(dataCount), DataTypeSwitch.toInt(timeSampleNum), shortData);

                result.put("dataArr",shortData.length > 0 ? shortData : null);

                break; //可选

            default:
                System.out.println("二维默认default");break;
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
