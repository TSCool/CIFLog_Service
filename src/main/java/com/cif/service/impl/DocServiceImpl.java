package com.cif.service.impl;

import com.cif.dao.DocMapper;
import com.cif.domain.DocBean;
import com.cif.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    DocMapper docMapper;

    /**
     * 根据id获取文档信息
     * @param id
     * @return
     */
    @Override
    public DocBean getDoc(int id) {
        return docMapper.getDoc(id);
    }

    /**
     * 根据文件名称获取文档信息
     * @param name
     * @return
     */
    @Override
    public DocBean getDocByFileName(String name,int categoryID) {
        return docMapper.getDocByFileName(name,categoryID);
    }

    /**
     * 根据井次id、ownerid获取所有的文档信息
     * @param categoryID
     * @return
     */
    @Override
    public DocBean[] getAllDocs(int categoryID) {
        return docMapper.getAllDocs(categoryID);
    }

    /**
     * 新增一条文档记录
     * @param cb
     */
    @Override
    public int addDoc(DocBean cb) {
        return docMapper.addDoc(cb);
    }

    /**
     * 根据id删除一条文档记录
     * @param id
     */
    @Override
    public int deleteDoc(int id) {
        return docMapper.deleteDoc(id);
    }

    /**
     * 更新一条文档记录
     * @param cb
     */
    @Override
    public int updateDoc(DocBean cb) {
        return docMapper.updateDoc(cb);
    }
}
