package com.cif.service;

import com.cif.domain.DocBean;
import org.apache.ibatis.annotations.Param;

public interface DocService {

    /**
     * 根据id获取文档信息
     * @param id
     * @return
     */
    public DocBean getDoc(int id);

    /**
     * 根据文件名称获取文档信息
     * @param name
     * @return
     */
    public DocBean getDocByFileName(String name,int categoryID);

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
