/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.dao;

import com.cif.domain.Curve3DBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yuanye
 */
@Mapper
@Repository
public interface Curve3DMapper {

    /**
     * 根据id查询三维曲线
     * @param id
     * @return
     */
    public Curve3DBean getCurve3D(int id);

    /**
     * 根据井次id和owner查询所有的三维曲线信息
     * @param categoryID
     * @return
     */
    public Curve3DBean[] getAllCurve3Ds(@Param("categoryID") int categoryID);

    /**
     * 新增一条三维曲线记录
     * @param cb
     */
    public int addCurve3D(Curve3DBean cb);

    /**
     * 根据id删除一条三维曲线记录
     * @param id
     */
    public int deleteCurve3D(int id);

    /**
     * 更新一条三维曲线记录
     * @param cb
     */
    public int updateCurve3D(Curve3DBean cb);
}
