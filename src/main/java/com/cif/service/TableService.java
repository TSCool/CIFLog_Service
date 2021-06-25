package com.cif.service;

import com.cif.domain.TableBean;
import org.apache.ibatis.annotations.Param;

public interface TableService {

    /**
     * 根据id获取表格信息
     * @param id
     * @return
     */
    public TableBean getTable(int id);

    /**
     * 根据井次id、owner获取所有的表格信息
     * @param categoryID
     * @return
     */
    public TableBean[] getAllTables(@Param("categoryID") int categoryID);

    /**
     * 根据井次id、loggingType、owner获取所有的表格信息
     * @param categoryID
     * @param loggingType
     * @return
     */
    public TableBean[] getTablesByLoggingType(@Param("categoryID") int categoryID, @Param("loggingType") byte loggingType);

    /**
     * 新增一条表格记录
     * @param tb
     */
    public int addTable(TableBean tb);

    /**
     * 根据id删除一条表格记录
     * @param id
     */
    public int deleteTable(int id);

    /**
     * 更新一条表格记录
     * @param tb
     */
    public int updateTable(TableBean tb);
}
