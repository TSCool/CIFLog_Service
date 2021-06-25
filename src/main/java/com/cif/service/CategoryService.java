package com.cif.service;

import com.cif.domain.CategoryBean;
import org.apache.ibatis.annotations.Param;

public interface CategoryService {

    /**
     * 根据id查询井次信息
     * @param id
     * @return
     */
    public CategoryBean getCategory(int id);

    /**
     * 根据井id和owner查询所有的井次信息
     * @param wellID
     * @return
     */
    public CategoryBean[] getAllCategorys(@Param("wellID") int wellID);

    /**
     * 新增井次信息
     * @param cb
     * @return
     */
    public int addCategory(CategoryBean cb);

    /**
     * 根据id删除一条井次信息记录
     * @param id
     * @return
     */
    public int deleteCategory(int id);

    /**
     * 更新一条井次信息记录
     * @param wb
     * @return
     */
    public int updateCategory(CategoryBean wb);
}
