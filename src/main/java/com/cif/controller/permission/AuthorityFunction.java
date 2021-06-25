package com.cif.controller.permission;

import com.cif.domain.*;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import com.cif.service.UserService;
import com.cif.service.WorkSpaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@Api(value = "权限功能模块",tags = "权限功能模块")
public class AuthorityFunction {

    @Autowired
    UserService userService;
    @Autowired
    RolesService rolesService;
    @Autowired
    WorkSpaceService  workSpaceService;
    @Autowired
    PermissionsService permissionsService;

    /**
     * 分配角色
     * @param currentUserId
     * @param userId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "分配角色")
    @RequestMapping(value = "/assign/role",method = RequestMethod.POST)
    public ResponseEntity assignRoles(int currentUserId, int userId, int roleId){
        Map<String,Object> data = new HashMap<String,Object>();

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(currentUserId);

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
            if(permissionsBean.getName().equals("x")){

                isx = true;

                break;

            }
        }

        //判断当前用户是否有分配角色的权限
        if(isx){

            int index = rolesService.assignRoles(userId,roleId);

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
     * 根据id删除用户角色关联表中对应的记录
     * @param currentUserId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除用户角色关联表中对应的记录")
    @RequestMapping(value = "/user/delete/role",method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(int currentUserId, int id){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = rolesService.deleteUserRoleByUserIdAndRoleId(id);

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
     * 根据userId查询所有的角色
     * @param currentUserId
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId查询所有的角色")
    @RequestMapping(value = "/user/find/allRole",method = RequestMethod.GET)
    public ResponseEntity findAllRoleByUser(int currentUserId, int userId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"r");//是否有读取的权限

        if(isx){

            Set<UserRolesBean> roleList = rolesService.findUserRoleByUserId(userId);

            data.put("data",roleList);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 根据id和新的角色id更新用户角色关联表中对应的记录
     * @param currentUserId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id和新的角色id更新用户角色关联表中对应的记录")
    @RequestMapping(value = "/user/update/role",method = RequestMethod.PUT)
    public ResponseEntity updateRole(int currentUserId, int id, int roleId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = rolesService.updateUserRole(id,roleId);

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
     * 为工区分配用户和对应的角色
     * @param currentUserId
     * @param workspaceId
     * @param userId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "为工区分配用户和对应的角色")
    @RequestMapping(value = "/assign/workspace/user/role",method = RequestMethod.POST)
    public ResponseEntity workspaceAssignUserRole(int currentUserId, int workspaceId, int userId, int roleId){
        Map<String,Object> data = new HashMap<String,Object>();

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(currentUserId);

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

            //如果有执行的权限
            if(permissionsBean.getName().equals("x")){

                isx = true;

                break;

            }
        }

        if(!isx){

            //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
            Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(currentUserId);

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

                //如果有执行的权限
                if(permissionsBean.getName().equals("x")){

                    //根据用户id查看对应的工区

                    isx = true;

                    break;

                }
            }

        }

        //判断当前用户是否有为工区分配角色的权限
        if(isx){
            int index = workSpaceService.workspaceAssignUserAndRoles(workspaceId,userId,roleId);

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
     * 根据userId查询所有的工区、用户、角色记录
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId查询所有的工区、用户、角色记录")
    @RequestMapping(value = "/ws/find/userRole",method = RequestMethod.GET)
    public ResponseEntity findAllWsRoleUserByUserId(int userId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(userId,"r");//是否有读取的权限

        if(isx){

            Set<WorkspaceRoleBean> roleList = rolesService.findAll();

            data.put("data",roleList);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            Set<WorkspaceRoleBean> roleList = rolesService.findAllByUserId(userId);

            data.put("data",roleList);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);
        }

    }

    /**
     * 根据id删除用户角色工区关联表中对应的记录
     * @param currentUserId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除用户角色工区关联表中对应的记录")
    @RequestMapping(value = "/ws/delete/userRole",method = RequestMethod.DELETE)
    public ResponseEntity deleteUserRole(int currentUserId, int id){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = rolesService.deleteWorkSpaceUserRoleById(id);

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
     * 根据id和新的用户id、新的角色id 更新工区、用户、角色关联表中对应的记录
     * @param currentUserId
     * @param id
     * @param userId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据id和新的用户id、新的角色id 更新工区、用户、角色关联表中对应的记录")
    @RequestMapping(value = "/ws/update/userRole",method = RequestMethod.PUT)
    public ResponseEntity updatePermission(int currentUserId, int id, int userId, int roleId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = rolesService.updateWsUserRole(id,userId,roleId);

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
     * 为角色分配权限
     * @param userId
     * @param roleId
     * @param permissionId
     * @return
     */
    @ApiOperation(value = "为角色分配权限")
    @RequestMapping(value = "/assign/permission",method = RequestMethod.POST)
    public ResponseEntity assignPermission(int userId, int roleId, int permissionId){

        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(userId,"x");//是否有执行的权限

        if(isx){

            int index = permissionsService.assignPermission(roleId,permissionId);

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
     * 根据id删除角色权限关联表中对应的记录
     * @param currentUserId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除角色权限关联表中对应的记录")
    @RequestMapping(value = "/role/delete/permission",method = RequestMethod.DELETE)
    public ResponseEntity deletePermission(int currentUserId, int id){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = permissionsService.deleteRolePermissionById(id);

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
     * 根据roleId查询所有的权限
     * @param currentUserId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据roleId查询所有的权限")
    @RequestMapping(value = "/role/find/allPermission",method = RequestMethod.GET)
    public ResponseEntity findAllPermissionByRole(int currentUserId, int roleId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"r");//是否有读取的权限

        if(isx){

            Set<RolePermissionsBean> roleList = permissionsService.findPermissionByRoleId(roleId);

            data.put("data",roleList);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 根据id和新的权限id更新角色权限关联表中对应的记录
     * @param currentUserId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id和新的角色id更新用户角色关联表中对应的记录")
    @RequestMapping(value = "/role/update/permission",method = RequestMethod.PUT)
    public ResponseEntity updatePermission(int currentUserId, int id, int permissionId){
        Map<String,Object> data = new HashMap<String,Object>();

        boolean isx = isHavePermission(currentUserId,"x");//是否有执行的权限

        if(isx){

            int index = permissionsService.updateRolePermission(id,permissionId);

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
     * 根据userId查询所有的权限
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId查询所有的权限")
    @RequestMapping(value = "/find/permission",method = RequestMethod.GET)
    public ResponseEntity selectPermissonByUserId(int userId){

        Map<String,Object> data = new HashMap<String,Object>();

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(userId);

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        data.put("data",permissionsBeansAll);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 根据userId和workspaceId查询所有的权限
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId和workspaceId查询所有的权限")
    @RequestMapping(value = "/ws/find/permission",method = RequestMethod.GET)
    public ResponseEntity selectPermissonByUserId(int userId,int workspaceId){

        Map<String,Object> data = new HashMap<String,Object>();

        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserIdAndWsId(userId,workspaceId);

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        data.put("data",permissionsBeansAll);

        data.put("status",200);

        return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 是否有对应的权限
     * @param id
     * @param operation
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
