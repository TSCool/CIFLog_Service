package com.cif.controller.permission;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.PermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@Api(value = "权限模块接口",tags = "权限模块接口")
public class PermissionController {

    @Autowired
    RolesService rolesService;
    @Autowired
    PermissionsService permissionsService;

    /**
     * 新增一个权限
     * @param request
     * @return
     */
    @ApiOperation(value = "新增一个权限")
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public ResponseEntity insertUser(HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"w");//是否有写入的权限

        if(isx){

            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成RolesBean实体
            PermissionsBean permissionsBean = JSONObject.parseObject(jsonObject.toJSONString(),PermissionsBean.class);

            int index = permissionsService.insertPermission(permissionsBean);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data, HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 删除一条角色信息记录
     * @param id
     */
    @ApiOperation(value = "删除一条角色信息记录")
    @RequestMapping(value = "/permission",method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(HttpServletRequest request,int id){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"x");//是否有执行的权限

        if(isx){

            //如果有权限 -- 先删除对应角色的 用户、权限、工区关联表中记录

            rolesService.deleteUserRole(id);

            permissionsService.deletePermissionRole(id);

            rolesService.deleteWorkSpaceUserRole(id);

            //删除完成后，删除该角色
            int index = rolesService.deleteRole(id);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 更新一条角色信息记录
     * @param request
     * @return
     */
    @ApiOperation(value = "更新一条角色信息记录")
    @RequestMapping(value = "/permission",method = RequestMethod.PUT)
    public ResponseEntity updateRole(HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"x");//是否有执行的权限

        if(isx){

            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成RolesBean实体
            RolesBean role = JSONObject.parseObject(jsonObject.toJSONString(),RolesBean.class);

            int index = rolesService.updateRole(role);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{
            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 获取所有的角色信息
     * @return
     */
    @ApiOperation(value = "获取所有的角色信息")
    @RequestMapping(value = "/permission/all",method = RequestMethod.GET)
    public ResponseEntity getAllRoles(int userId){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(userId,"r");//是否有查看的权限

        if(isx){

            RolesBean[] rolesBeans = rolesService.getAllRoles();

            if(rolesBeans != null && rolesBeans.length > 0){

                data.put("data",rolesBeans);

                data.put("status",200);

            }else{

                data.put("data","暂无信息");

                data.put("status",200);

            }

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 是否有对应的权限
     * @param id
     * @return
     */
    public boolean isHavePermission(int id,String operation){
        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(id);

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isx = false;//是否有执行的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有执行的权限,则能够分配角色
            if(permissionsBean.getName().equals(operation)){

                isx = true;

                break;

            }
        }

        return isx;
    }

}
