package com.cif.dao;

import com.cif.domain.PermissionsBean;
import com.cif.domain.RolePermissionsBean;
import com.cif.domain.RolesBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface PermissionsMapper {

    /**
     * 新增一条权限信息记录
     * @param p
     */
    public int insertPermission(PermissionsBean p);

    /**
     * 删除一条权限信息记录
     * @param id
     */
    public int deletePermission(int id);

    /**
     * 更新一条权限信息记录
     * @param p
     */
    public int updatePermission(PermissionsBean p);

    /**
     * 获取所有的权限
     * @return
     */
    public RolesBean[] getAllPermissions();

    /**
     * 根据角色id查询拥有的权限
     * @param roleId
     * @return
     */
    public Set<PermissionsBean> findByRoleId(int roleId);

    /**
     * 为角色分配权限
     * @param roleId
     * @param permissionId
     * @return
     */
    public int assignPermission(@Param("roleId") int roleId, @Param("permissionId") int permissionId);

    /**
     * 根据roleId在role_permission表中查询权限
     * @param roleId
     * @return
     */
    public Set<RolePermissionsBean> findPermissionByRoleId(@Param("roleId") int roleId);

    /**
     * 根据roleId删除对应的角色、权限关联表role_permission中的记录
     * @param id
     * @return
     */
    public int deletePermissionRole(int id);

    /**
     * 根据id删除对应的角色权限关联表role_permission中记录
     * @param id
     * @return
     */
    public int deleteRolePermissionById(int id);

    /**
     * 根据id和新的权限id更新对应的角色权限关联表role_permission中记录
     * @param id
     * @param permissionId
     * @return
     */
    public int updateRolePermission(@Param("id") int id, @Param("permissionId") int permissionId);

}
