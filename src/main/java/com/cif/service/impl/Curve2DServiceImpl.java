package com.cif.service.impl;

import com.cif.dao.Curve2DMapper;
import com.cif.domain.Curve2DBean;
import com.cif.service.Curve2DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Curve2DServiceImpl implements Curve2DService {

    @Autowired
    Curve2DMapper curve2DMapper;

    /**
     * 根据id获取二维曲线信息
     * @param id
     * @return
     */
    @Override
    public Curve2DBean getCurve2D(int id) {
        return curve2DMapper.getCurve2D(id);
    }

    /**
     * 根据井次id、owner获取所有的二维曲线信息
     * @param categoryID
     * @return
     */
    @Override
    public Curve2DBean[] getAllCurve2Ds(int categoryID) {
        return curve2DMapper.getAllCurve2Ds(categoryID);
    }

    /**
     * 新增一条二维曲线记录
     * @param cb
     */
    @Override
    public int addCurve2D(Curve2DBean cb) {
        return curve2DMapper.addCurve2D(cb);
    }

    /**
     * 根据id删除一条
     * @param id
     */
    @Override
    public int deleteCurve2D(int id) {
        return curve2DMapper.deleteCurve2D(id);
    }

    /**
     * 更新一条二维曲线记录
     * @param cb
     */
    @Override
    public int updateCurve2D(Curve2DBean cb) {
        return curve2DMapper.updateCurve2D(cb);
    }
}
