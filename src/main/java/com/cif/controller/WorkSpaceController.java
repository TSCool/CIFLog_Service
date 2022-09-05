package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.PermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.domain.UserBean;
import com.cif.domain.WorkSpaceBean;
import com.cif.security.SecurityUtils;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import com.cif.service.WorkSpaceService;
import com.cif.utils.constant.FilePath;
import com.cif.utils.security.SecurityUtil;
import com.cif.utils.util.FileDeal;
import com.cif.utils.util.GetJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Api(value="工区接口模块",tags = "工区接口模块")
public class WorkSpaceController {

    @Autowired
    WorkSpaceService workSpaceService;

    @Autowired
    RolesService rolesService;

    @Autowired
    PermissionsService permissionsService;

    @Value("${file.path}")
    private String CIFLogFiles;

    /**
     * 根据id获取工区信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取工区信息")
    @RequestMapping(value = "/ws",method = RequestMethod.GET)
    public ResponseEntity getWorkSpace(HttpServletRequest request, int id){

        Map<String,Object> data = new HashMap<>();

        WorkSpaceBean workSpace = null;

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(Integer.parseInt(request.getHeader("userId")));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isRead = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("r")){

                isRead = true;

                break;

            }
        }

        //如果一级没有权限
        if(!isRead){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(Integer.parseInt(request.getHeader("userId")));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("r")){

                    //根据用户id查看对应的工区
                    workSpace = workSpaceService.getWorkSpace(id);

                    break;

                }
            }

        }else{

            workSpace = workSpaceService.getWorkSpace(id);

        }

        if(workSpace != null){

            data.put("data",workSpace);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 根据owner查询所有的工区
     * @param owner
     * @return
     */
    @ApiOperation(value = "根据owner查询所有的工区")
        @RequestMapping(value = "/ws/all",method = RequestMethod.GET)
        public ResponseEntity getAllWorkSpaces(String owner){

        Map<String,Object> data = new HashMap<>();

        //查询的所有工区
        WorkSpaceBean[] workSpace = null;

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(owner.equals("") ? null : Integer.parseInt(owner));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isRead = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("r")){

                isRead = true;

                break;

            }
        }

        //如果一级没有权限
        if(!isRead){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(owner.equals("") ? null : Integer.parseInt(owner));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("r")){

                    //根据用户id查看对应的工区
                    workSpace = workSpaceService.getWorkSpacesByUserId(owner.equals("") ? null : Integer.parseInt(owner));

                    break;

                }
            }

        }else{

            //获取所有的工区
            workSpace = workSpaceService.getAll();

        }


        if(workSpace != null && workSpace.length > 0){

            data.put("data", workSpace);

            data.put("status",200);

        }else{

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * 新增一个工区
     * @param request
     * @return
     */
    @ApiOperation(value = "新增工区")
    @RequestMapping(value = "/ws",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public ResponseEntity addWorkSpace(HttpServletRequest request){

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(Integer.parseInt(request.getHeader("userId")));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isWrite = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("w")){

                isWrite = true;

                break;

            }
        }

        if(!isWrite){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(Integer.parseInt(request.getHeader("userId")));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("w")){

                    //根据用户id查看对应的工区

                    isWrite = true;

                    break;

                }
            }

        }

        if(isWrite){
            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成WorkSpaceBean实体
            WorkSpaceBean wsb = JSONObject.parseObject(jsonObject.toJSONString(),WorkSpaceBean.class);

            Map<String,Object> data = new HashMap<>();

            //调用service层新增方法实现
            int index = workSpaceService.addWorkSpace(wsb);

            //如果新增成功，将ID返回
            if(index > 0){

                data.put("data",wsb.getId());

            }

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            Map<String,Object> data = new HashMap<>();

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);

        }

    }

    /**
     * 根据id删除一条工区记录
     * @param id
     */
    @ApiOperation(value = "根据id删除一条工区记录")
    @RequestMapping(value = "/ws",method = RequestMethod.DELETE)
    public ResponseEntity deleteWorkSpace(HttpServletRequest request,int id){

        Map<String,Object> data = new HashMap<>();

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(Integer.parseInt(request.getHeader("userId")));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isx = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("x")){

                isx = true;

                break;

            }
        }

        if(!isx){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(Integer.parseInt(request.getHeader("userId")));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("x")){

                    //根据用户id查看对应的工区

                    isx = true;

                    break;

                }
            }

        }

        if(isx){
            //如果有权限 -- 先删除对应角色的 用户、工区关联表中记录
            rolesService.deleteWorkSpaceUserRoleByWsId(id);

            int index = workSpaceService.deleteWorkSpace(id);

            /*通过工区id，拼接 文件夹 的路径*/
            String filepath = CIFLogFiles + id;

            /*判断记录是否删除成功*/
            if(index > 0){

                /*删除记录成功，则删除对应的文件夹*/
                FileDeal.deleteDiretory(filepath);

            }

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
     * 更新一条工区记录信息
     * @param request
     */
    @ApiOperation(value = "更新工区记录")
    @RequestMapping(value = "/ws",method = RequestMethod.PUT,produces={"application/json;charset=utf-8"})
    public ResponseEntity updateWorkSpace(HttpServletRequest request){

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(Integer.parseInt(request.getHeader("userId")));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isx = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("x")){

                isx = true;

                break;

            }
        }

        if(!isx){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(Integer.parseInt(request.getHeader("userId")));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("x")){

                    //根据用户id查看对应的工区

                    isx = true;

                    break;

                }
            }

        }

        if(isx){

            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成WorkSpaceBean实体
            WorkSpaceBean wsb = JSONObject.parseObject(jsonObject.toJSONString(),WorkSpaceBean.class);

            Map<String,Object> data = new HashMap<>();

            int index = workSpaceService.updateWorkSpace(wsb);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            Map<String,Object> data = new HashMap<>();

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);

        }

    }

    /**
     * 根据dataSourceId获取工区信息
     * @param dataSourceId
     * @return
     */
    @ApiOperation(value = "根据dataSourceId获取工区信息")
    @RequestMapping(value = "/ws/dataSourceId",method = RequestMethod.GET)
    public ResponseEntity getWorkSpaceByDataSourceId(HttpServletRequest request, int dataSourceId){

        Map<String,Object> data = new HashMap<>();

        WorkSpaceBean workSpace = null;

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(Integer.parseInt(request.getHeader("userId")));

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isRead = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("r")){

                isRead = true;

                break;

            }
        }

        //如果一级没有权限
        if(!isRead){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(Integer.parseInt(request.getHeader("userId")));

            for(RolesBean rolesBean:wsRoleList){

                //根绝角色获取所有的权限
                Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

                for(PermissionsBean permissionsBean:permissionsBeansList){

                    //查看用户所有的权限
                    permissionsBeansAll.add(permissionsBean);

                }

            }

            //遍历权限
            for(PermissionsBean permissionsBean:permissionsBeansAll){

                //如果有读的权限
                if(permissionsBean.getName().equals("r")){

                    //根据用户id查看对应的工区
                    workSpace = workSpaceService.getWorkSpaceByDataSourceId(dataSourceId);

                    break;

                }
            }

        }else{

            workSpace = workSpaceService.getWorkSpaceByDataSourceId(dataSourceId);

        }

        if(workSpace != null){

            data.put("data",workSpace);

            data.put("status",200);

        }else{

            data.put("data","暂无信息");

            data.put("status",200);

        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

}
