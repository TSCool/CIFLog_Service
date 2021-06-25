package com.cif.service.impl;

import com.cif.dao.Curve1DMapper;
import com.cif.domain.Curve1DBean;
import com.cif.service.Curve1DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Curve1DServiceImpl implements Curve1DService {
    @Autowired
    Curve1DMapper curve1DMapper;

    /**
     * 根据id查询1维曲线
     * @param id
     * @return
     */
    @Override
    public Curve1DBean getCurve1D(int id) {
        return curve1DMapper.getCurve1D(id);
    }

    /**
     * 根据井次id和owner查询所有的一维曲线信息
     * @param categoryID
     * @return
     */
    @Override
    public Curve1DBean[] getAllCurve1Ds(int categoryID) { return curve1DMapper.getAllCurve1Ds(categoryID); }

    /**
     * 新增一条一维曲线记录
     * @param cb
     */
    @Override
    public int addCurve1D(Curve1DBean cb) { return curve1DMapper.addCurve1D(cb); }

    /**
     * 根据id删除一条一维曲线记录
     * @param id
     */
    @Override
    public int deleteCurve1D(int id) { return curve1DMapper.deleteCurve1D(id); }

    /**
     * 更新一条一维曲线记录
     * @param cb
     */
    @Override
    public int updateCurve1D(Curve1DBean cb) { return curve1DMapper.updateCurve1D(cb); }
}
