package com.cif.service.impl;

import com.cif.dao.RolesMapper;
import com.cif.domain.*;
import com.cif.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesMapper rolesMapper;

    @Override
    public Set<UserRolesBean> findUserRoleByUserId(int userId) {
        return rolesMapper.findUserRoleByUserId(userId);
    }

    /**
     * 根据userId在user_roles表中查询角色
     * @param userId
     * @return
     */
    @Override
    public Set<RolesBean> findRoleByUserId(int userId) {
        return rolesMapper.findRoleByUserId(userId);
    }

    /**
     * 根据userId在workspace_roles表中查询角色
     * @param userId
     * @return
     */
    @Override
    public Set<RolesBean> findWorkspaceRoleByUserId(int userId) {
        return rolesMapper.findWorkspaceRoleByUserId(userId);
    }

    @Override
    public Set<RolesBean> findRoleByUserIdAndWsId(int userId, int workspaceId) {
        return rolesMapper.findRoleByUserIdAndWsId(userId,workspaceId);
    }

    @Override
    public Set<WorkspaceRoleBean> findAll() {
        return rolesMapper.findAll();
    }

    @Override
    public Set<WorkspaceRoleBean> findAllByUserId(int userId) {
        return rolesMapper.findAllByUserId(userId);
    }

    @Override
    public int updateWsUserRole(int id, int userId, int roleId) {
        return rolesMapper.updateWsUserRole(id,userId,roleId);
    }

    /**
     * 为指定用户分配角色
     * @param userId
     * @return
     */
    @Override
    public int assignRoles(int userId, int roleId) {
        return rolesMapper.assignRoles(userId,roleId);
    }

    /**
     * 新增一条角色信息记录
     * @param u
     */
    @Override
    public int insertRole(RolesBean u) {
        return rolesMapper.insertRole(u);
    }

    /**
     * 删除一条角色信息记录
     * @param id
     */
    @Override
    public int deleteRole(int id) {
        return rolesMapper.deleteRole(id);
    }

    /**
     * 根据roleId删除对应的用户角色关联表中记录
     * @param id
     * @return
     * 10000 10000
     */
    @Override
    public int deleteUserRole(int id) {
        return rolesMapper.deleteUserRole(id);
    }

    @Override
    public int deleteUserRoleByUserIdAndRoleId(int id) {
        return rolesMapper.deleteUserRoleByUserIdAndRoleId(id);
    }

    /**
     * 根据roleId删除对应的工区、用户、角色、权限关联表中的记录
     * @param id
     * @return
     */
    @Override
    public int deleteWorkSpaceUserRole(int id) {
        return rolesMapper.deleteWorkSpaceUserRole(id);
    }

    @Override
    public int deleteWorkSpaceUserRoleByWsId(int id) {
        return rolesMapper.deleteWorkSpaceUserRoleByWsId(id);
    }

    @Override
    public int deleteWorkSpaceUserRoleById(int id) {
        return rolesMapper.deleteWorkSpaceUserRoleById(id);
    }

    /**
     * 更新一条角色信息记录
     * @param u
     */
    @Override
    public int updateRole(RolesBean u) {
        return rolesMapper.updateRole(u);
    }

    @Override
    public int updateUserRole(int id, int roleId) {
        return rolesMapper.updateUserRole(id,roleId);
    }

    /**
     * 获取所有的角色
     * @return
     */
    @Override
    public RolesBean[] getAllRoles() {
        return rolesMapper.getAllRoles();
    }


}
