package com.cif.service.impl;

import com.cif.dao.WellMapper;
import com.cif.domain.WellBean;
import com.cif.service.WellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WellServiceImpl implements WellService {

    @Autowired
    WellMapper wellMapper;

    /**
     * 根据id获取井信息
     * @param id
     * @return
     */
    @Override
    public WellBean getWell(int id) {
        return wellMapper.getWell(id);
    }

    /**
     * 根据工区id和owner获取所有的井信息
     * @param workSpaceID
     * @return
     */
    @Override
    public WellBean[] getAllWells(int workSpaceID) {
        return wellMapper.getAllWells(workSpaceID);
    }

    /**
     * 新增一条井记录
     * @param wb
     */
    @Override
    public int addWell(WellBean wb) { return wellMapper.addWell(wb); }

    /**
     * 根据id删除一条井记录
     * @param id
     */
    @Override
    public int deleteWell(int id) { return wellMapper.deleteWell(id); }

    /**
     * 更新一条井记录
     * @param wb
     */
    @Override
    public int updateWell(WellBean wb) { return wellMapper.updateWell(wb); }

    /**
     * 根据名称、工区id、owner查找对应的记录信息
     * @param name
     * @param workSpaceID
     * @return
     */
    @Override
    public WellBean findWell(String name, int workSpaceID) { return wellMapper.findWell(name,workSpaceID); }
}
