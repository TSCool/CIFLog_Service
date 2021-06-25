/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.dao;

import com.cif.domain.DocBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 文档
 * @author yuanye
 */
@Mapper
@Repository
public interface DocMapper {

    /**
     * 根据id获取文档信息
     * @param id
     * @return
     */
    public DocBean getDoc(int id);

    /**
     * 根据id获取文档信息
     * @param name
     * @return
     */
    public DocBean getDocByFileName(@Param("name") String name,@Param("categoryID") int categoryID);

    /**
     * 根据井次id、ownerid获取所有的文档信息
     * @param categoryID
     * @return
     */
    public DocBean[] getAllDocs(@Param("categoryID") int categoryID);

    /**
     * 新增一条文档记录
     * @param cb
     */
    public int addDoc(DocBean cb);

    /**
     * 根据id删除一条文档记录
     * @param id
     */
    public int deleteDoc(int id);

    /**
     * 更新一条文档记录
     * @param cb
     */
    public int updateDoc(DocBean cb);
}
