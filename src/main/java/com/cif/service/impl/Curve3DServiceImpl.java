package com.cif.service.impl;

import com.cif.dao.Curve3DMapper;
import com.cif.domain.Curve3DBean;
import com.cif.service.Curve3DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Curve3DServiceImpl implements Curve3DService {
    @Autowired
    Curve3DMapper curve3DMapper;

    /**
     * 根据id查询三维曲线
     * @param id
     * @return
     */
    @Override
    public Curve3DBean getCurve3D(int id) {
        return curve3DMapper.getCurve3D(id);
    }

    /**
     * 根据井次id和owner查询所有的三维曲线信息
     * @param categoryID
     * @return
     */
    @Override
    public Curve3DBean[] getAllCurve3Ds(int categoryID) {
        return curve3DMapper.getAllCurve3Ds(categoryID);
    }

    /**
     * 新增一条三维曲线记录
     * @param cb
     */
    @Override
    public int addCurve3D(Curve3DBean cb) {
        return curve3DMapper.addCurve3D(cb);
    }

    /**
     * 根据id删除一条三维曲线记录
     * @param id
     */
    @Override
    public int deleteCurve3D(int id) {
        return curve3DMapper.deleteCurve3D(id);
    }

    /**
     * 更新一条三维曲线记录
     * @param cb
     */
    @Override
    public int updateCurve3D(Curve3DBean cb) {
        return curve3DMapper.updateCurve3D(cb);
    }
}
