package com.cif.service;

import com.cif.domain.WorkSpaceBean;
import org.apache.ibatis.annotations.Param;

public interface WorkSpaceService {

    /**
     * 根据id获取工区信息
     * @param id
     * @return
     */
    public WorkSpaceBean getWorkSpace(int id);

    /**
     * 根据owner查询所有的工区
     * @param owner
     * @return
     */
    public WorkSpaceBean[] getAllWorkSpaces(Integer owner);

    /**
     * 根据userId查询所有的工区
     * @param userId
     * @return
     */
    public WorkSpaceBean[] getWorkSpacesByUserId(Integer userId);

    /**
     * 获取所有的工区
     * @return
     */
    public WorkSpaceBean[] getAll();

    /**
     * 新增一个工区
     * @param wsb
     */
    public int addWorkSpace(WorkSpaceBean wsb);

    /**
     * 根据id删除一条工区记录
     * @param id
     */
    public int deleteWorkSpace(int id);

    /**
     * 更新一条工区记录信息
     * @param wsb
     */
    public int updateWorkSpace(WorkSpaceBean wsb);

    /**
     * 为工区分配用户和对应的角色
     * @param userId
     * @return
     */
    public int workspaceAssignUserAndRoles(@Param("workspaceId") int workspaceId, @Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 根据datasourceId获取工区信息
     * @param datasourceId
     * @return
     */
    public WorkSpaceBean getWorkSpaceByDataSourceId(int datasourceId);
}
