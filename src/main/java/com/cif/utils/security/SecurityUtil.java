package com.cif.utils.security;

import com.cif.domain.PermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.domain.UserBean;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class SecurityUtil {

    @Autowired
    private static RolesService rolesService;

    @Autowired
    private static PermissionsService permissionsService;

    /**
     * 是否可读 -- 关联user_roles
     * @param userId
     * @return
     */
    public static Boolean isR(Integer userId){

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

        boolean isRead = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("r")){

                isRead = true;

                break;

            }
        }

        return isRead;
    }

    /**
     * 是否可读 -- 关联workspace_roles
     * @param userId
     * @return
     */
    public static Boolean isR2(Integer userId){

        boolean isRead = false;//是否有读取的权限

        //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
        Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(userId);

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

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

                isRead = true;

                break;

            }
        }

        return isRead;
    }

    /**
     * 是否可写
     * @param userId
     * @return
     */
    public static Boolean isW(Integer userId){
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

        boolean isWrite = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("w")){

                isWrite = true;

                break;

            }
        }

        return isWrite;
    }

    public static Boolean isW2(Integer userId){

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        boolean isWrite = false;//是否有读取的权限

        //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
        Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(userId);

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

        return isWrite;

    }

    /**
     * 是否可执行
     * @param userId
     * @return
     */
    public static Boolean isX(Integer userId){
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

        boolean isx = false;//是否有读取的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有读的权限,则能查看所有的工区
            if(permissionsBean.getName().equals("x")){

                isx = true;

                break;

            }
        }

        return isx;
    }

    public static Boolean isX2(Integer userId){

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        boolean isx = false;//是否有读取的权限

        //根据id获取用户在该工区的角色 -- 用户角色 关联表 -- workspace_roles
        Set<RolesBean> wsRoleList = rolesService.findWorkspaceRoleByUserId(userId);

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

        return isx;

    }


}
