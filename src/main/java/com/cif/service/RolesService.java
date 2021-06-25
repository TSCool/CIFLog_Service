package com.cif.service;

import com.cif.domain.*;
import org.apache.ibatis.annotations.Param;

import java.security.Permission;
import java.util.Set;

public interface RolesService {

    /*角色表role的操作*/

    /**
     * 新增一条角色信息记录
     * @param u
     */
    public int insertRole(RolesBean u);

    /**
     * 删除一条角色信息记录
     * @param id
     */
    public int deleteRole(int id);

    /**
     * 更新一条角色信息记录
     * @param u
     */
    public int updateRole(RolesBean u);

    /**
     * 获取所有的角色
     * @return
     */
    public RolesBean[] getAllRoles();


    /*user_roles表的操作*/

    /**
     * 根据userId在user_roles表中查询角色
     * @param userId
     * @return
     */
    public Set<RolesBean> findRoleByUserId(@Param("userId") int userId);

    /**
     * 为指定用户分配角色 -- user_roles表中新增记录
     * @param userId
     * @return
     */
    public int assignRoles(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 根据userId在user_roles表中查询所有的关联记录
     * @param userId
     * @return
     */
    public Set<UserRolesBean> findUserRoleByUserId(@Param("userId") int userId);

    /**
     * 根据roleId删除对应的用户角色关联表user_roles中记录
     * @param id
     * @return
     */
    public int deleteUserRole(int id);

    /**
     * 根据id删除对应的用户角色关联表user_roles中记录
     * @param id
     * @return
     */
    public int deleteUserRoleByUserIdAndRoleId(int id);

    /**
     * 根据id和新的角色id更新对应的用户角色关联表user_roles中记录
     * @param id
     * @param roleId
     * @return
     */
    public int updateUserRole(@Param("id") int id, @Param("roleId") int roleId);



    /*用户、角色、工区表的操作*/

    /**
     * 根据userId在workspace_roles表中查询角色
     * @param userId
     * @return
     */
    public Set<RolesBean> findWorkspaceRoleByUserId(@Param("userId") int userId);

    /**
     * 根据userId和workspaceId查询角色
     * @param userId
     * @return
     */
    public Set<RolesBean> findRoleByUserIdAndWsId(@Param("userId") int userId,@Param("workspaceId") int workspaceId);

    /**
     * 查询记录
     * @return
     */
    public Set<WorkspaceRoleBean> findAll();

    /**
     * 根据userId查询对应的记录
     * @param userId
     * @return
     */
    public Set<WorkspaceRoleBean> findAllByUserId(@Param("userId") int userId);

    /**
     * 根据id和新的用户id、角色id更新对应的工区用户角色关联表workspace_roles中记录
     * @param id
     * @param roleId
     * @return
     */
    public int updateWsUserRole(@Param("id") int id, @Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 根据roleId删除对应的工区、用户、角色关联workspace_roles表中的记录
     * @param id
     * @return
     */
    public int deleteWorkSpaceUserRole(int id);

    /**
     * 根据workspaceId删除对应的工区、用户、角色关联workspace_roles表中的记录
     * @param id
     * @return
     */
    public int deleteWorkSpaceUserRoleByWsId(int id);

    /**
     * 根据id删除对应的工区、用户、角色关联workspace_roles表中的记录
     * @param id
     * @return
     */
    public int deleteWorkSpaceUserRoleById(int id);

}
