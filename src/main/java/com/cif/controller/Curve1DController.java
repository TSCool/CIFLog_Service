package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.Curve1DBean;
import com.cif.domain.Curve2DBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.Curve1DService;
import com.cif.service.WellService;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.DataTypeSwitch;
import com.cif.utils.util.GetJson;
import com.cif.utils.wrapper.Curve1DRwWrapper;
import com.cif.utils.wrapper.Curve2DRwWrapper;
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
@Api(value = "一维曲线接口模块",tags = "一维曲线接口模块")
public class Curve1DController {
    @Autowired
    Curve1DService curve1DService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取1维曲线信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取1维曲线信息")
    @RequestMapping(value = "/1d",method = RequestMethod.GET)
    public ResponseEntity getCurve1DById(int id){

        Map<String,Object> data = new HashMap<>();

        Curve1DBean curve1D = curve1DService.getCurve1D(id);

        if(curve1D != null){

            data.put("data",curve1D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id和owner查询所有的一维曲线信息
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id和owner查询所有的一维曲线信息")
    @RequestMapping(value = "/1d/all",method = RequestMethod.GET)
    public ResponseEntity getAllCurve1Ds(String categoryID,String owner){

        Map<String,Object> data = new HashMap<>();

        Curve1DBean[] curve1D = curve1DService.getAllCurve1Ds(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        if(curve1D != null && curve1D.length > 0){

            data.put("data",curve1D);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条一维曲线记录
     * @param request
     */
    @ApiOperation(value = "新增一条一维曲线记录")
    @RequestMapping(value = "/1d",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addCurve1D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve1DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve1DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve1DService.addCurve1D(cb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",cb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条一维曲线记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条一维曲线记录")
    @RequestMapping(value = "/1d",method = RequestMethod.DELETE)
    public ResponseEntity deleteCurve1D(int id){

        Map<String,Object> data = new HashMap<>();

        /*删除记录之前 -- 根据id获取该曲线的信息*/
        Curve1DBean curve1D = curve1DService.getCurve1D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve1D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve1D.getFile();

        /*删除该曲线记录*/
        int index = curve1DService.deleteCurve1D(id);

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
     * 更新一条一维曲线记录
     * @param request
     */
    @ApiOperation(value = "更新一维曲线记录")
    @RequestMapping(value = "/1d",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateCurve1D(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        Curve1DBean cb = JSONObject.parseObject(jsonObject.toJSONString(),Curve1DBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = curve1DService.updateCurve1D(cb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据一维曲线id读取一维曲线文件的数据
     * @return
     */
    @ApiOperation(value = "根据一维曲线id读取一维曲线文件的数据,")
    @RequestMapping(value = "/1d/data",method = RequestMethod.GET)
    public ResponseEntity readData(String id,String startDataIndex,String dataCount){
        Map<String,Object> data = new HashMap<>();

        float[] fileData = new float[dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount)];
        /*根据id获取1维曲线信息*/
        Curve1DBean curve1D = curve1DService.getCurve1D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve1D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve1D.getFile();

        Curve1DRwWrapper wrapper = new Curve1DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        int num = wrapper.readData(startDataIndex.equals("") || startDataIndex == null ? null : Integer.parseInt(startDataIndex)
                ,dataCount.equals("") || dataCount == null ? null : Integer.parseInt(dataCount),fileData);

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();
        result.put("num",num);
        result.put("dataArr",fileData);

        if(fileData.length > 0){
            data.put("data",result);
            data.put("status",200);
        }else{
            data.put("status",200);
        }
        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 根据起始点修改一维曲线文件的数据
     * @return
     */
    @ApiOperation(value = "根据起始点修改一维曲线文件的数据")
    @RequestMapping(value = "/1d/changeDepthRange",method = RequestMethod.GET)
    public ResponseEntity changeDepthRange(int id,byte dataType,int startDataIndex,int endDataIndex,boolean preserved){
        Map<String,Object> data = new HashMap<>();

        /*根据id获取1维曲线信息*/
        Curve1DBean curve1D = curve1DService.getCurve1D(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(curve1D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve1D.getFile();

        Curve1DRwWrapper wrapper = new Curve1DRwWrapper(filepath);

        try {
            wrapper.changeDepthRange(dataType,startDataIndex,endDataIndex,preserved);
            data.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            data.put("status",500);
        }

        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 写入一维曲线数据
     * @param startDataIndex
     * @param dataCount
     * @return
     */
    @ApiOperation(value = "写入一维曲线数据")
    @RequestMapping(value = "/1d/writedata",method = RequestMethod.POST)
    public ResponseEntity writeData(String id, String startDataIndex, String dataCount, String type,String allData){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取2维曲线信息*/
        Curve1DBean curve1D = curve1DService.getCurve1D(id.equals("") || id == null ? null : Integer.parseInt(id));

        /*根据井次id获取井id*/
        int wellId = getWellId(curve1D.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + curve1D.getFile();

        Curve1DRwWrapper wrapper = new Curve1DRwWrapper(filepath);

        /*通过路径读取数据,并返回读取数量*/
        boolean isFlag = false;

        /*构建接口返回数据*/
        Map<String,Object> result = new HashMap<>();

        //将字符串转换成对应的数组
        allData = allData.substring(1,(allData.length()-1));
        String[] strData = allData.split(",");

        System.out.println("查看一维数据类型"+type);

        /*根据类型参数类型，判断数组类型*/
        switch(type){
            case "float[]":

                float[] floatData = new float[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){
                    if(strData[i].equals("null")){
                        floatData[i] = Float.parseFloat("-99999");
                    }else{
                        floatData[i] = Float.parseFloat(strData[i]);
                    }
                }

                isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount), floatData);

                result.put("dataArr",floatData.length > 0 ? floatData : null);

                break; //可选

            case "short[]":

                short[] shortData = new short[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){

                    if(strData[i].equals("null")){
                        shortData[i] = Short.parseShort("-99999");
                    }else{
                        shortData[i] = Short.parseShort(strData[i]);
                    }
                }

                isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),shortData);

                result.put("dataArr",shortData.length > 0 ? shortData : null);

                break; //可选

            case "double[]":

                double[] doubleData = new double[strData.length];

                //字符串数组转换成short类型
                for(int i=0;i<strData.length;i++){

                    if(strData[i].equals("null")){
                        doubleData[i] = Double.parseDouble("-99999");
                    }else{
                        doubleData[i] = Double.parseDouble(strData[i]);
                    }
                }

                isFlag = wrapper.writeData(DataTypeSwitch.toInt(startDataIndex), DataTypeSwitch.toInt(dataCount),doubleData);

                result.put("dataArr",doubleData.length > 0 ? doubleData : null);

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
