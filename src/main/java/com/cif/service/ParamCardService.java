package com.cif.service;

import com.cif.domain.ParamCardBean;
import org.apache.ibatis.annotations.Param;

public interface ParamCardService {

    /**
     * 根据id获取参数卡片信息
     * @param id
     * @return
     */
    public ParamCardBean getParamCard(int id);

    /**
     * 根据name获取参数卡片信息
     * @param name
     * @return
     */
    public ParamCardBean getParamCardByName(String name);

    /**
     * 根据井次id、owner获取所有的参数卡片数据
     * @param categoryID
     * @return
     */
    public ParamCardBean[] getAllParamCards(@Param("categoryID") int categoryID);

    /**
     * 新增一条参数卡片记录
     * @param pcb
     */
    public int addParamCard(ParamCardBean pcb);

    /**
     * 删除一条参数卡片记录
     * @param id
     */
    public int deleteParamCard(int id);

    /**
     * 更新一条参数卡片记录
     * @param pc
     */
    public int updateParamCard(ParamCardBean pc);
}
