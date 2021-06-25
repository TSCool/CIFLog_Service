package com.cif.service.impl;

import com.cif.dao.PermissionsMapper;
import com.cif.domain.PermissionsBean;
import com.cif.domain.RolePermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Autowired
    private PermissionsMapper permissionsMapper;

    /**
     * 新增一条权限信息记录
     * @param p
     */
    @Override
    public int insertPermission(PermissionsBean p) {
        return permissionsMapper.insertPermission(p);
    }

    /**
     * 删除一条权限信息记录
     * @param id
     */
    @Override
    public int deletePermission(int id) {
        return permissionsMapper.deletePermission(id);
    }

    /**
     * 更新一条权限信息记录
     * @param p
     */
    @Override
    public int updatePermission(PermissionsBean p) {
        return permissionsMapper.updatePermission(p);
    }

    /**
     * 获取所有的权限
     * @return
     */
    @Override
    public RolesBean[] getAllPermissions() {
        return permissionsMapper.getAllPermissions();
    }

    /**
     * 根据角色id查询拥有的权限
     * @param roleId
     * @return
     */
    @Override
    public Set<PermissionsBean> findByRoleId(int roleId) {
        return permissionsMapper.findByRoleId(roleId);
    }

    /**
     * 为角色分配权限
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public int assignPermission(int roleId, int permissionId) {
        return permissionsMapper.assignPermission(roleId,permissionId);
    }

    /**
     * 根据roleId在role_permission表中查询权限
     * @param roleId
     * @return
     */
    @Override
    public Set<RolePermissionsBean> findPermissionByRoleId(int roleId) {
        return permissionsMapper.findPermissionByRoleId(roleId);
    }

    /**
     * 根据roleId删除对应的角色、权限关联表role_permission中的记录
     * @param id
     * @return
     */
    @Override
    public int deletePermissionRole(int id) {
        return permissionsMapper.deletePermissionRole(id);
    }

    /**
     * 根据id删除对应的角色权限关联表role_permission中记录
     * @param id
     * @return
     */
    @Override
    public int deleteRolePermissionById(int id) {
        return permissionsMapper.deleteRolePermissionById(id);
    }

    /**
     * 根据id和新的权限id更新对应的角色权限关联表role_permission中记录
     * @param id
     * @param permissionId
     * @return
     */
    @Override
    public int updateRolePermission(int id, int permissionId) {
        return permissionsMapper.updateRolePermission(id,permissionId);
    }
}
