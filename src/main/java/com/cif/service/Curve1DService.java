package com.cif.service;

import com.cif.domain.Curve1DBean;
import org.apache.ibatis.annotations.Param;

public interface Curve1DService {

    /**
     * 根据id获取一维曲线信息
     * @param id
     * @return
     */
    public Curve1DBean getCurve1D(int id);

    /**
     * 根据井次id和owner查询所有的一维曲线信息
     * @param categoryID
     * @return
     */
    public Curve1DBean[] getAllCurve1Ds(@Param("categoryID") int categoryID);

    /**
     * 新增一条一维曲线记录
     * @param cb
     */
    public int addCurve1D(Curve1DBean cb);

    /**
     * 根据id删除一条一维曲线记录
     * @param id
     */
    public int deleteCurve1D(int id);

    /**
     * 更新一条一维曲线记录
     * @param cb
     */
    public int updateCurve1D(Curve1DBean cb);
}
