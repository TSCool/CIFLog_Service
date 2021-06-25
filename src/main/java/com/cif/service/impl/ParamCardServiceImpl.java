package com.cif.service.impl;

import com.cif.dao.ParamCardMapper;
import com.cif.domain.ParamCardBean;
import com.cif.service.ParamCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamCardServiceImpl implements ParamCardService {

    @Autowired
    ParamCardMapper paramCardMapper;

    /**
     * 根据id获取参数卡片信息
     * @param id
     * @return
     */
    @Override
    public ParamCardBean getParamCard(int id) {
        return paramCardMapper.getParamCard(id);
    }

    /**
     * 根据name获取参数卡片信息
     * @param name
     * @return
     */
    @Override
    public ParamCardBean getParamCardByName(String name) {
        return paramCardMapper.getParamCardByName(name);
    }

    /**
     * 根据井次id、owner获取所有的参数卡片数据
     * @param categoryID
     * @return
     */
    @Override
    public ParamCardBean[] getAllParamCards(int categoryID) {
        return paramCardMapper.getAllParamCards(categoryID);
    }

    /**
     * 新增一条参数卡片记录
     * @param pcb
     */
    @Override
    public int addParamCard(ParamCardBean pcb) {
        return paramCardMapper.addParamCard(pcb);
    }

    /**
     * 删除一条参数卡片记录
     * @param id
     */
    @Override
    public int deleteParamCard(int id) {
        return paramCardMapper.deleteParamCard(id);
    }

    /**
     * 更新一条参数卡片记录
     * @param pc
     */
    @Override
    public int updateParamCard(ParamCardBean pc) {
        return paramCardMapper.updateParamCard(pc);
    }
}
