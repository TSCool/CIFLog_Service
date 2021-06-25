package com.cif.service;

import com.cif.domain.Curve2DBean;
import org.apache.ibatis.annotations.Param;

public interface Curve2DService {

    /**
     * 根据id获取二维曲线信息
     * @param id
     * @return
     */
    public Curve2DBean getCurve2D(int id);

    /**
     * 根据井次id、owner获取所有的二维曲线信息
     * @param categoryID
     * @return
     */
    public Curve2DBean[] getAllCurve2Ds(@Param("categoryID") int categoryID);

    /**
     * 新增一条二维曲线记录
     * @param cb
     */
    public int addCurve2D(Curve2DBean cb);

    /**
     * 根据id删除一条
     * @param id
     */
    public int deleteCurve2D(int id);

    /**
     * 更新一条二维曲线记录
     * @param cb
     */
    public int updateCurve2D(Curve2DBean cb);
}
