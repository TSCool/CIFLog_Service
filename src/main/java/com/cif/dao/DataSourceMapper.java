/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.dao;

import com.cif.domain.DatasourceBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yuanye
 */
@Mapper
@Repository
public interface DataSourceMapper {

    /**
     * 根据id获取数据源信息
     * @param id
     * @return
     */
    public DatasourceBean getDatasource(int id);

    /**
     * 获取所有的数据源
     * @return
     */
    public DatasourceBean[] getAll();

    /**
     * 新增一个数据源
     * @param das
     */
    public int addDatasource(DatasourceBean das);

    /**
     * 根据id删除一条数据源记录
     * @param id
     */
    public int deleteDatasource(int id);

    /**
     * 更新一条数据源记录信息
     * @param das
     */
    public int updateDatasource(DatasourceBean das);
    
}
