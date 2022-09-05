package com.cif.service;

import com.cif.domain.DatasourceBean;
import com.cif.domain.WorkSpaceBean;

/**
 * Description: 数据源业务层
 * Author liutianshuo
 *
 * @Date 2022/8/25
 * @Version 1.0
 **/
public interface DatasourceService {

    /**
     * 根据id获取数据源信息
     * @param id
     * @return
     */
    public DatasourceBean getDataSource(int id);

    /**
     * 获取所有的数据源
     * @return
     */
    public DatasourceBean[] getAll();

    /**
     * 新增一个数据源
     * @param das
     */
    public int addDataSource(DatasourceBean das);

    /**
     * 根据id删除一条数据源记录
     * @param id
     */
    public int deleteDataSource(int id);

    /**
     * 更新一条数据源记录信息
     * @param das
     */
    public int updateDataSource(DatasourceBean das);

}
