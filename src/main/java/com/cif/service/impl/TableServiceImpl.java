package com.cif.service.impl;

import com.cif.dao.TableMapper;
import com.cif.domain.TableBean;
import com.cif.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableMapper tableMapper;

    /**
     * 根据id获取表格信息
     * @param id
     * @return
     */
    @Override
    public TableBean getTable(int id) {
        return tableMapper.getTable(id);
    }

    /**
     * 根据井次id、owner获取所有的表格信息
     * @param categoryID
     * @return
     */
    @Override
    public TableBean[] getAllTables(int categoryID) {
        return tableMapper.getAllTables(categoryID);
    }

    /**
     * 根据井次id、loggingType、owner获取所有的表格信息
     * @param categoryID
     * @param loggingType
     * @return
     */
    @Override
    public TableBean[] getTablesByLoggingType(int categoryID, byte loggingType) {
        return tableMapper.getTablesByLoggingType(categoryID,loggingType);
    }

    /**
     * 新增一条表格记录
     * @param tb
     */
    @Override
    public int addTable(TableBean tb) {
        return tableMapper.addTable(tb);
    }

    /**
     * 根据id删除一条表格记录
     * @param id
     */
    @Override
    public int deleteTable(int id) {
        return tableMapper.deleteTable(id);
    }

    /**
     * 更新一条表格记录
     * @param tb
     */
    @Override
    public int updateTable(TableBean tb) {
        return tableMapper.updateTable(tb);
    }
}
