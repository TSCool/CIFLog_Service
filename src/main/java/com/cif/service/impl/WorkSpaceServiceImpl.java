package com.cif.service.impl;

import com.cif.dao.WorkSpaceMapper;
import com.cif.domain.WorkSpaceBean;
import com.cif.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    WorkSpaceMapper workSpaceMapper;

    /**
     * 根据id获取工区信息
     * @param id
     * @return
     */
    @Override
    public WorkSpaceBean getWorkSpace(int id) {
        return workSpaceMapper.getWorkSpace(id);
    }

    /**
     * 根据owner查询所有的工区
     * @param owner
     * @return
     */
    @Override
    public WorkSpaceBean[] getAllWorkSpaces(Integer owner) { return workSpaceMapper.getAllWorkSpaces(owner); }

    @Override
    public WorkSpaceBean[] getWorkSpacesByUserId(Integer userId) {
        return workSpaceMapper.getWorkSpacesByUserId(userId);
    }

    @Override
    public WorkSpaceBean[] getAll() {
        return workSpaceMapper.getAll();
    }

    /**
     * 新增一个工区
     * @param wsb
     */
    @Override
    public int addWorkSpace(WorkSpaceBean wsb) { return workSpaceMapper.addWorkSpace(wsb); }

    /**
     * 根据id删除一条工区记录
     * @param id
     */
    @Override
    public int deleteWorkSpace(int id) { return workSpaceMapper.deleteWorkSpace(id); }

    /**
     * 更新一条工区记录信息
     * @param wsb
     */
    @Override
    public int updateWorkSpace(WorkSpaceBean wsb) { return workSpaceMapper.updateWorkSpace(wsb); }

    /**
     * 为工区分配用户和对应的角色
     * @param userId
     * @return
     */
    @Override
    public int workspaceAssignUserAndRoles(int workspaceId, int userId, int roleId) {
        return workSpaceMapper.workspaceAssignUserAndRoles(workspaceId,userId,roleId);
    }
}
