package com.cif.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.domain.CategoryBean;
import com.cif.domain.ParamEntity;
import com.cif.domain.TableBean;
import com.cif.domain.WellBean;
import com.cif.service.CategoryService;
import com.cif.service.TableService;
import com.cif.service.WellService;
import com.cif.utils.baseutil.array.*;
import com.cif.utils.util.DataTypeSwitch;
import com.cif.utils.constant.FilePath;
import com.cif.utils.dataengine.TableFields;
import com.cif.utils.dataengine.TableRecords;
import com.cif.utils.util.FileDeal;
import com.cif.utils.util.GetJson;
import com.cif.utils.util.StructureEntity;
import com.cif.utils.wrapper.TableRwWrapper;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value="表格接口模块",tags = "表格接口模块")
public class TableController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    WellService wellService;

    @Autowired
    TableService tableService;

    @Value("${file.path}")
    private String CIFLogFiles;

    private int workSpaceId = 0;
    private int wellId = 0;
    private int tableId = 0;

    /**
     * 根据id获取表格信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取表格信息")
    @RequestMapping(value = "/table",method = RequestMethod.GET)
    public ResponseEntity getTable(int id){

        Map<String,Object> data = new HashMap<>();

        TableBean table = tableService.getTable(id);

        if(table != null){

            data.put("data",table);

            data.put("status",200);

        }else{

            data.put("data",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id、owner获取所有的表格信息
     * @param categoryID
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id、owner获取所有的表格信息")
    @RequestMapping(value = "/table/all",method = RequestMethod.GET)
    public ResponseEntity getAllTables(String categoryID,String owner){

        Map<String,Object> data = new HashMap<>();

        TableBean[] tables = tableService.getAllTables(categoryID.equals("") || categoryID == null ? null : Integer.parseInt(categoryID));

        if(tables != null && tables.length > 0){

            data.put("data",tables);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据井次id、loggingType、owner获取所有的表格信息
     * @param categoryID
     * @param loggingType
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据井次id、loggingType、owner获取所有的表格信息")
    @RequestMapping(value = "/table/all/by",method = RequestMethod.GET)
    public ResponseEntity getTablesByLoggingType(int categoryID,byte loggingType,Integer owner){

        Map<String,Object> data = new HashMap<>();

        TableBean[] tables = tableService.getTablesByLoggingType(categoryID,loggingType);

        if(tables != null && tables.length > 0){

            data.put("data",tables);

            data.put("status",200);

        }else{

            data.put("data",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一条表格记录
     * @return
     */
    @ApiOperation(value = "新增一条表格记录")
    @RequestMapping(value = "/table",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
        public ResponseEntity addTable(@RequestBody TableBean tb){

        System.out.println("查看参数"+tb);
//        //获取到JSONObject
//        JSONObject jsonObject = GetJson.getJSONParam(request);
//
//        //JSON对象转换成WorkSpaceBean实体
//        TableBean tb = JSONObject.parseObject(jsonObject.toJSONString(),TableBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = tableService.addTable(tb);

        //如果新增成功，将ID返回
        if(index > 0){
            data.put("data",tb.getId());
        }

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据id删除一条表格记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条表格记录")
    @RequestMapping(value = "/table",method = RequestMethod.DELETE)
    public ResponseEntity deleteTable(int id){

        Map<String,Object> data = new HashMap<>();

        /*删除记录之前 -- 根据id获取该曲线的信息*/
        TableBean table = tableService.getTable(id);

        /*根据井次id获取井id*/
        int wellId = getWellId(table.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + table.getFile();

        int index = tableService.deleteTable(id);

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
     * 更新一条表格记录
     * @param request
     * @return
     */
    @ApiOperation(value = "更新一条表格记录")
    @RequestMapping(value = "/table",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateTable(HttpServletRequest request){

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成WorkSpaceBean实体
        TableBean tb = JSONObject.parseObject(jsonObject.toJSONString(),TableBean.class);

        Map<String,Object> data = new HashMap<>();

        int index = tableService.updateTable(tb);

        data.put("data",index);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 读取表格数据
\     * @return
     */
    @ApiOperation(value = "读取表格数据")
    @RequestMapping(value = "/table/data",method = RequestMethod.POST)
    public ResponseEntity readTableFields(int workSpaceId, int wellId, int tableId,@RequestBody TableFields fields){

//        //获取到JSONObject
//        JSONObject jsonObject = GetJson.getJSONParam(request);
//
//        //JSON对象转换成TableRecords实体
//        TableFields fields = JSONObject.parseObject(jsonObject.toJSONString(),TableFields.class);

        Map<String,Object> data = new HashMap<>();

        /*根据id获取表格信息*/
        TableBean table = tableService.getTable(tableId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + table.getFile();

        TableRwWrapper wrapper = new TableRwWrapper(filepath);

        try {
            wrapper.readTableFields(fields);

            //构建TableFields数组
            Map<String,Object> tableFields = structureTableFields(fields);

            data.put("data",tableFields);
            data.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            data.put("status",500);
        }

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 接收参数
     * @param workSpaceId
     * @param wellId
     * @param tableId
     * @return
     */
    @ApiOperation(value = "接收参数")
    @RequestMapping(value = "/table/receiveParams",method = RequestMethod.GET)
    public ResponseEntity writeTableRecords(int workSpaceId, int wellId, int tableId){

        this.workSpaceId = workSpaceId;

        this.wellId = wellId;

        this.tableId = tableId;

        Map<String,Object> data = new HashMap<>();

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 在表格中写入数据
     * @param jsonObject
     * @return
     */
    @ApiOperation(value = "在表格中写入数据")
    @RequestMapping(value = "/table/writeData",method = RequestMethod.POST)
    public ResponseEntity writeTableRecords(@RequestBody JSONObject jsonObject){

        long startTime = System.currentTimeMillis();
        //接口返回容器
        Map<String,Object> data = new HashMap<>();

        //接收json参数
        ParamEntity res = JSONObject.parseObject(jsonObject.toJSONString(),ParamEntity.class);

        //将结果转换成对象
        JSONObject jsonObj = new JSONObject();

        jsonObj = JSONObject.parseObject(res.getData());

        //根据json对象获取tableFields对象各属性的值
        JSONObject tableObj = JSONObject.parseObject(jsonObj.get("tableFields").toString());

        //构建TableFields实体
        TableFields tableFields = StructureEntity.structureTableFields(tableObj);

        //通过json对象获取records数组的数据
        JSONArray array = jsonObj.getJSONArray("records");

        ArrayList<TableRecords.OneRowRecords> records = StructureEntity.structureRecords(array,tableFields);

        //通过json对象获取recordNum属性的值
        int recordNum = (int)jsonObj.get("recordNum");

        //构建TableRecords实体
        TableRecords tableRecords = new TableRecords();

        tableRecords.setRecordNum(recordNum);

        tableRecords.setRecords(records);

        tableRecords.setTableFields(tableFields);

        /*根据id获取表格信息*/
        TableBean table = tableService.getTable(res.getId());

        /*根据文档id获取井id*/
        int wellId = getWellId(table.getCategoryID());

        /*根据井id获取工区id*/
        int workSpaceId = getWorkSpaceId(wellId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + table.getFile();

        TableRwWrapper wrapper = new TableRwWrapper(filepath);

        try {

            wrapper.writeTableRecords(tableRecords);

            data.put("status",200);

        }catch (Exception e){

            e.printStackTrace();

            data.put("status",500);

        }

        data.put("status",200);

        long endTime = System.currentTimeMillis();

        System.out.println("运行时间："+(endTime - startTime) + "ms");

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 读取表格记录
     * @param workSpaceId
     * @param wellId
     * @param tableId
     * @param tableRecords
     * @return
     */
    @ApiOperation(value = "读取表格记录")
    @RequestMapping(value = "/table/readDataRecord",method = RequestMethod.GET)
    public ResponseEntity readTableRecords(int workSpaceId, int wellId, int tableId, TableRecords tableRecords){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取表格信息*/
        TableBean table = tableService.getTable(tableId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + table.getFile();

        TableRwWrapper wrapper = new TableRwWrapper(filepath);
        wrapper.readTableRecords(tableRecords);

        //重新构建records元素的数据
        if(tableRecords.getRecords() != null){

            ArrayList<TableRecords.OneRowRecords> records = rebuildRecords(tableRecords.getRecords());

            tableRecords.setRecords(records);

        }

        //构建TableFields数组
        Map<String,Object> tableFields = null;

        if(tableRecords.getTableFields() != null){

            TableFields field = tableRecords.getTableFields();

            tableFields = structureTableFields(field);

        }

        //结构返回的数据结构
        Map<String,Object> result = new HashMap<>();

        result.put("records",tableRecords.getRecords());

        result.put("recordNum",tableRecords.getRecordNum());

        result.put("comparator",tableRecords.getComparator());

        result.put("tableFields",tableFields);

        if(tableRecords != null){

            try {

                data.put("data",result);

                data.put("status",200);

            }catch (Exception e){

                e.printStackTrace();

                data.put("status",500);

            }

        }

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 读取表格记录2
     * @param workSpaceId
     * @param wellId
     * @param tableId
     * @param indexToRead
     * @param readingNumber
     * @param tableRecords
     * @return
     */
    @ApiOperation(value = "读取表格记录 -- 带参数indexToRead")
    @RequestMapping(value = "/table/readDataRecord/indexToRead",method = RequestMethod.GET)
    public ResponseEntity indexToRead(int workSpaceId, int wellId, int tableId, String indexToRead, String readingNumber,TableRecords tableRecords){

        Map<String,Object> data = new HashMap<>();

        /*根据id获取表格信息*/
        TableBean table = tableService.getTable(tableId);

        /*通过井id、工区id，拼接文件的路径*/
        String filepath = CIFLogFiles + workSpaceId + File.separator + wellId + File.separator + table.getFile();

        //创建table的mapper实例
        TableRwWrapper wrapper = new TableRwWrapper(filepath);
        wrapper.readTableRecords(DataTypeSwitch.toInt(indexToRead),DataTypeSwitch.toInt(readingNumber),tableRecords);

        //重新构建records元素的数据
        if(tableRecords.getRecords() != null){
            ArrayList<TableRecords.OneRowRecords> records = rebuildRecords(tableRecords.getRecords());;
            tableRecords.setRecords(records);
        }

        //构建TableFields数组
        Map<String,Object> tableFields = null;

        if(tableRecords.getTableFields() != null){

            TableFields field = tableRecords.getTableFields();

            tableFields = structureTableFields(field);

        }

        //结构返回的数据结构
        Map<String,Object> result = new HashMap<>();

        result.put("records",tableRecords.getRecords());

        result.put("recordNum",tableRecords.getRecordNum());

        result.put("comparator",tableRecords.getComparator());

        result.put("tableFields",tableFields);

        if(tableRecords != null){

            try {

                data.put("data",result);

                data.put("status",200);

            }catch (Exception e){

                e.printStackTrace();

                data.put("status",500);

            }

        }

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 构建TableFields的数据结构
     * @param field
     * @return
     */
    public Map<String,Object> structureTableFields(TableFields field){

        Map<String,Object> tableFields = new HashMap<>();

        tableFields.put("fieldNum",field.getFieldNum());

        tableFields.put("fieldNames",field.getFieldNames());

        tableFields.put("fieldUnits",field.getFieldUnits());

        tableFields.put("dataTypes",field.getDataTypes());

        tableFields.put("defaultValues",field.getDefaultValues());

        ArrayList<ValueArray> defaultValue = field.getDefaultValues();

        //防止defaultValue属性有空指针的问题
        for (int i=0;i<defaultValue.size();i++){

            //如果为空，咋给记录加一个默认值
            if(defaultValue.get(i) == null){

//                StringArray strArr = new StringArray();
//
//                strArr.set_size(0);
//
//                strArr.set_capacity(0);
//
//                strArr.setIncreaseSize(0);
//
//                String[] strArray = new String[1];
//
//                strArray[0] = "0";
//
//                strArr.setArray(strArray);
//
//                defaultValue.set(i,strArr);
                defaultValue.set(i,null);

            }

        }

        tableFields.put("enumStrings",field.getEnumStrings());

        tableFields.put("dataNums",field.getDataNums());

        if(field.getTemplateName() != null){

            tableFields.put("templateName",field.getTemplateName());

        }

        return tableFields;
    }

    /**
     * 重新构建records集合的数据
     * @param records
     * @return
     */
    public ArrayList<TableRecords.OneRowRecords> rebuildRecords(ArrayList<TableRecords.OneRowRecords> records){

        //遍历records记录
        for(int j=0;j<records.size();j++){

            //获取子集下面的recordsList记录集合
            ArrayList<ValueArray> recordsList = records.get(j).getRecords();

            //遍历子集下面recordsList记录集合
            for(int i=0;i<recordsList.size();i++){

                //如果记录为空，则给一个默认值
                if(recordsList.get(i) == null){

                    //创建 ValueArray 下子对象 StringArray 作为默认值
                    StringArray strArr = new StringArray();

                    strArr.set_size(0);

                    strArr.set_capacity(0);

                    strArr.setIncreaseSize(0);

                    String[] strArray = new String[1];

                    strArray[0] = "0";

                    strArr.setArray(strArray);

                    recordsList.set(i,strArr);

                }

                //判断如果类型为字节类型，将其转换成字符串类型
                if(recordsList.get(i) != null && recordsList.get(i).getClass().getName().equals("com.cif.utils.baseutil.array.ByteArray")){

                    ByteArray byteArr = (ByteArray)recordsList.get(i);

                    StringArray strArr = new StringArray();

                    strArr.set_size(byteArr.get_size());

                    strArr.set_capacity(byteArr.get_capacity());

                    strArr.setIncreaseSize(byteArr.getIncreaseSize());

                    String str = new String(byteArr.getArray());

                    String[] strArray = new String[1];

                    strArray[0] = str;

                    strArr.setArray(strArray);

                    recordsList.set(i,strArr);

                }

            }

            records.get(j).setRecords(recordsList);

        }

        return records;

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
