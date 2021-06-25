package com.cif.service.impl;

import com.cif.dao.CategoryMapper;
import com.cif.domain.CategoryBean;
import com.cif.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据id查询井次信息
     * @param id
     * @return
     */
    @Override
    public CategoryBean getCategory(int id) {
        return categoryMapper.getCategory(id);
    }

    /**
     * 根据井id和owner查询所有的井次信息
     * @param wellID
     * @return
     */
    @Override
    public CategoryBean[] getAllCategorys(int wellID) {
        return categoryMapper.getAllCategorys(wellID);
    }

    /**
     * 新增井次信息
     * @param cb
     * @return
     */
    @Override
    public int addCategory(CategoryBean cb) {
        return categoryMapper.addCategory(cb);
    }

    /**
     * 根据id删除一条井次信息记录
     * @param id
     * @return
     */
    @Override
    public int deleteCategory(int id) {
        return categoryMapper.deleteCategory(id);
    }

    /**
     * 更新一条井次信息记录
     * @param wb
     * @return
     */
    @Override
    public int updateCategory(CategoryBean wb) {
        return categoryMapper.updateCategory(wb);
    }
}
