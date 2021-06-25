/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.dao;

import com.cif.domain.WellBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yuanye
 */
@Mapper
@Repository
public interface WellMapper {

    /**
     * 根据id获取井信息
     * @param id
     * @return
     */
    public WellBean getWell(int id);

    /**
     * 根据工区id和owner获取所有的井信息
     * @param workSpaceID
     * @return
     */
    public WellBean[] getAllWells(@Param("workSpaceID") int workSpaceID);

    /**
     * 新增一条井记录
     * @param wb
     */
    public int addWell(WellBean wb);

    /**
     * 根据id删除一条井记录
     * @param id
     */
    public int deleteWell(int id);

    /**
     * 更新一条井记录
     * @param wb
     */
    public int updateWell(WellBean wb);

    /**
     * 根据名称、工区id、owner查找对应的记录信息
     * @param name
     * @param workSpaceID
     * @return
     */
    public WellBean findWell(@Param("name") String name, @Param("workSpaceID") int workSpaceID);
}
