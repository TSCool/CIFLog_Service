package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.*;
import com.cif.utils.PathUtil;
import com.cif.utils.constant.FilePath;
import com.cif.utils.util.CifColorFormat;
import com.cif.utils.util.GetJson;
import com.cif.utils.util.XmlReadWrite;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.h2.command.dml.Delete;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "层位接口模块", tags = "层位接口模块")
public class ZoneController {

    public static final String PATH_WORKSPACE_DATA = "_workspaceData";
    public static final String PATH_CARDS = "Cards";
    public static final String PATH_SEISMIC = "Seismic";
    public static final String PATH_ZONES = "Zones";
    public static final String PATH_ZONE_XML = "zones.xml";
    public static final String EXT_XML = "xml";
    public static final String ZONE_XML_NODE_ROOT = "ZONES";
    public static final String ZONE_XML_NODE_ZONE = "Zone";
    public static final String ATTRIBUTE_ZONE_NAME = "ZoneName";
    public static final String ATTRIBUTE_ZONE_COMMIT = "Commit";
    public static final String ATTRIBUTE_ZONE_COLOR = "Color";

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 写入xml文件
     * @param request
     * @return
     */
    @ApiOperation(value = "写入xml文件")
    @RequestMapping(value = "/zone/write",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity writeZoneXmlFile(HttpServletRequest request){

        Map<String,Object> data = new HashMap<>();

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成ZonesParentBean实体，包含层位实体与工区id
        ZonesParentBean zonesParentBean = JSONObject.parseObject(jsonObject.toJSONString(),ZonesParentBean.class);

        //获取层位实体
        Zones zones = zonesParentBean.getZones();

        //获取工区id，用来拼接文件路径
        String workspaceId = Integer.toString(zonesParentBean.getWorkspaceID());

        /*服务器存储路径 通过工区id，拼接文件的路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones" + File.separator + zones.zoneType + ".xml";

        try{
            //开始存储xml文件
            XmlReadWrite xmlReadWrite = new XmlReadWrite(zonesFile);

            xmlReadWrite.createDoc();

            Element root = xmlReadWrite.writeElement(ZONE_XML_NODE_ROOT);

            xmlReadWrite.writeNode(root);

            for (ZoneItem zoneItem : zones.zoneItems) {

                Element node = xmlReadWrite.writeElement(ZONE_XML_NODE_ZONE);

                node.setAttribute(ATTRIBUTE_ZONE_NAME, zoneItem.getZoneName());

                node.setAttribute(ATTRIBUTE_ZONE_COMMIT, zoneItem.getDescription());

                node.setAttribute(ATTRIBUTE_ZONE_COLOR, CifColorFormat.getCifFormatColorString(zoneItem.getColor()));

                root.appendChild(node);

            }

            xmlReadWrite.writeXMLToFile();

            //接口返回状态
            data.put("status",200);

        }catch (Exception e){

            //接口返回状态 -- 文件上传失败
            data.put("status",400);

            e.printStackTrace();

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 写入xml文件内容
     * @param zones
     * @param workspaceId
     * @return
     */
    public void writeZoneXmlFileFunc(Zones zones,String workspaceId){

        /*服务器存储路径 通过工区id，拼接文件的路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones" + File.separator + zones.zoneType + ".xml";

        try{
            //开始存储xml文件
            XmlReadWrite xmlReadWrite = new XmlReadWrite(zonesFile);

            xmlReadWrite.createDoc();

            Element root = xmlReadWrite.writeElement(ZONE_XML_NODE_ROOT);

            xmlReadWrite.writeNode(root);

            for (ZoneItem zoneItem : zones.zoneItems) {

                Element node = xmlReadWrite.writeElement(ZONE_XML_NODE_ZONE);

                node.setAttribute(ATTRIBUTE_ZONE_NAME, zoneItem.getZoneName());

                node.setAttribute(ATTRIBUTE_ZONE_COMMIT, zoneItem.getDescription());

                node.setAttribute(ATTRIBUTE_ZONE_COLOR, CifColorFormat.getCifFormatColorString(zoneItem.getColor()));

                root.appendChild(node);

            }

            xmlReadWrite.writeXMLToFile();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    /**
     * 根据文件名称和工区id解析xml
     * @param zoneType
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据文件名称和工区id解析xml")
    @RequestMapping(value = "/zone/parse",method = RequestMethod.GET)
    public ResponseEntity parseZoneXMLFile(String zoneType,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*服务器存储路径 通过工区id，拼接文件的路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones" + File.separator + zoneType + ".xml";

        try{
            //开始解析xml文件
            XmlReadWrite xmlReadWrite = new XmlReadWrite(zonesFile);

            xmlReadWrite.parseDoc();

            Node root = xmlReadWrite.getRootNode();

            ArrayList<Node> zoneNodes = xmlReadWrite.findNodeList(root, ZONE_XML_NODE_ZONE);

            if (zoneNodes == null) {

                throw new IllegalStateException(zonesFile + java.util.ResourceBundle.getBundle("cif/cifplus/mul/io/Bundle").getString("CifMulWorkspaceEx.parseZoneXMLFile.message"));

            }

            ZoneItem[] zoneItems = new ZoneItem[zoneNodes.size()];

            String[] colorArr = new String[zoneItems.length];

            for (int i = 0; i < zoneItems.length; i++) {

                zoneItems[i] = new ZoneItem();

                zoneItems[i].setZoneName(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_NAME));

                zoneItems[i].setColor(CifColorFormat.getColor(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_COLOR)));

                zoneItems[i].setDescription(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_COMMIT));

                colorArr[i] = xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_COLOR);

            }

            //构建解析后的层位实体，作为接口的返回结果
            Zones zones = new Zones();

            zones.zoneType = zoneType;

            zones.zoneItems = zoneItems;

            Map<String,Object> map = new HashMap<>();

            map.put("zones",zones);

            map.put("colorArr",colorArr);

            data.put("data",map);

            data.put("status",200);

        }catch (Exception e){

            //解析失败
            data.put("status",400);

            e.printStackTrace();

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 解析xml的方法
     * @param zoneType
     * @return
     */
    public Zones parseZoneXMLFileFunc(String zoneType,String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*服务器存储路径 通过工区id，拼接文件的路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones" + File.separator + zoneType + ".xml";

        try{
            //开始解析xml文件
            XmlReadWrite xmlReadWrite = new XmlReadWrite(zonesFile);

            xmlReadWrite.parseDoc();

            Node root = xmlReadWrite.getRootNode();

            ArrayList<Node> zoneNodes = xmlReadWrite.findNodeList(root, ZONE_XML_NODE_ZONE);

            if (zoneNodes == null) {

                throw new IllegalStateException(zonesFile + java.util.ResourceBundle.getBundle("cif/cifplus/mul/io/Bundle").getString("CifMulWorkspaceEx.parseZoneXMLFile.message"));

            }

            ZoneItem[] zoneItems = new ZoneItem[zoneNodes.size()];

            for (int i = 0; i < zoneItems.length; i++) {

                zoneItems[i] = new ZoneItem();

                zoneItems[i].setZoneName(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_NAME));

                zoneItems[i].setColor(CifColorFormat.getColor(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_COLOR)));

                zoneItems[i].setDescription(xmlReadWrite.parseAttribute(zoneNodes.get(i), ATTRIBUTE_ZONE_COMMIT));

            }

            //构建解析后的层位实体，作为接口的返回结果
            Zones zones = new Zones();

            zones.zoneType = zoneType;

            zones.zoneItems = zoneItems;

            return zones;

        }catch (Exception e){

            e.printStackTrace();

            //解析失败
            return null;

        }
    }

    /**
     * 根据工区id获取文件夹下所有的文件
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据工区id获取文件夹下所有的文件")
    @RequestMapping(value = "/zone/type",method = RequestMethod.GET)
    public ResponseEntity getZoneTypes(String workspaceId){

        Map<String,Object> data = new HashMap<>();

        /*服务器存储路径 通过工区id，拼接文件夹的路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones";

        try{

            File pathFile = new File(zonesFile);

            File[] zoneFiles = null;

            //判断文件夹是否存在，不存在的话则创建
            if (!pathFile.exists() || !pathFile.isDirectory()) {

                pathFile.mkdirs();

                zoneFiles = new File[]{};

            }else{
                //获取文件列表
                File[] files = pathFile.listFiles();

                ArrayList<File> fileList = new ArrayList<File>();

                for (File file : files) {

                    if (PathUtil.getExtension(file.getName()).toLowerCase().endsWith(EXT_XML)) {

                        fileList.add(file);

                    }

                }
                zoneFiles = fileList.toArray(new File[0]);
            }


            String[] zoneTypes = new String[zoneFiles.length];

            for (int i = 0; i < zoneFiles.length; i++) {

                zoneTypes[i] = PathUtil.getBaseName(zoneFiles[i].getName());

            }

            data.put("data",zoneTypes);

            data.put("status",200);

        }catch (Exception e){

            //解析失败
            data.put("status",400);

            e.printStackTrace();

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据名称和工区id删除xml文件
     * @param zoneType
     * @param workspaceId
     * @return
     */
    @ApiOperation(value = "根据名称和工区id删除xml文件")
    @RequestMapping(value = "/zone/delete",method = RequestMethod.DELETE)
    public ResponseEntity deleteZones(String zoneType,String workspaceId){
        Map<String,Object> data = new HashMap<>();

        /*拼接文件路径*/
        String zonesFile = CIFLogFiles + workspaceId + File.separator + "zones" + File.separator + zoneType + ".xml";

        File file = new File(zonesFile);
        Boolean isFlag = true;
        if(!file.exists()){
            isFlag = false;
        }else{
            isFlag = file.delete();
        }

        data.put("data",isFlag);

        data.put("status",200);

        return new ResponseEntity(data, HttpStatus.OK);
    }

    /**
     * 更新一天xml记录
     * @param request
     * @return
     */
    @ApiOperation(value = "更新一天xml记录")
    @RequestMapping(value = "/zone/update",method = RequestMethod.PUT)
    public ResponseEntity updateZones(HttpServletRequest request){

        Map<String,Object> data = new HashMap<>();

        //获取到JSONObject
        JSONObject jsonObject = GetJson.getJSONParam(request);

        //JSON对象转换成CategoryBean实体
        ZonesUpdateBean zonesUpdateBean = JSONObject.parseObject(jsonObject.toJSONString(),ZonesUpdateBean.class);

        Zones zones = parseZoneXMLFileFunc(zonesUpdateBean.getZoneType(),zonesUpdateBean.getWorkspaceId());

        if (zones == null) {

            Zones newZones = new Zones();

            newZones.zoneType = zonesUpdateBean.getZoneType();

            newZones.zoneItems = zonesUpdateBean.getZoneItems();

            writeZoneXmlFileFunc(newZones,zonesUpdateBean.getWorkspaceId());

        } else {

            if (zonesUpdateBean.isClear()) {

                zones.zoneType = zonesUpdateBean.getZoneType();

                zones.zoneItems = zonesUpdateBean.getZoneItems();

            } else {

                ArrayList<ZoneItem> differentItems = new ArrayList<ZoneItem>();

                for (int i = 0; i < zonesUpdateBean.getZoneItems().length; i++) {

                    ZoneItem zoneItem = getZoneItemFrom(zones.zoneItems, zonesUpdateBean.getZoneItems()[i].getZoneName());

                    if (zoneItem == null) {

                        differentItems.add(zonesUpdateBean.getZoneItems()[i]);

                    } else {

                        zoneItem.copyFrom(zonesUpdateBean.getZoneItems()[i]);

                    }

                }

                if (differentItems.size() > 0) {

                    for (int i = 0; i < zones.zoneItems.length; i++) {

                        differentItems.add(zones.zoneItems[i]);

                    }

                    zones.zoneItems = differentItems.toArray(new ZoneItem[0]);

                }

            }

            writeZoneXmlFileFunc(zones,zonesUpdateBean.getWorkspaceId());

        }
        data.put("status",200);
        return new ResponseEntity(data,HttpStatus.OK);
    }

    /**
     * 更新xml使用
     * @param zoneItems
     * @param zoneName
     * @return
     */
    private ZoneItem getZoneItemFrom(ZoneItem[] zoneItems, String zoneName) {
        for (ZoneItem zoneItem : zoneItems) {
            if (zoneItem.getZoneName().equalsIgnoreCase(zoneName)) {
                return zoneItem;
            }
        }
        return null;
    }

}
