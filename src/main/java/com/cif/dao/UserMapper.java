/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cif.dao;

import com.cif.domain.UserBean;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author yuanye
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据名称获取用户信息
     * @param name
     * @return
     */
    public UserBean getUserByName(String name);

    /**
     * 根据名称和密码获取用户信息
     * @param name
     * @return
     */
    public UserBean getUserByNameAndPassword(@Param("name") String name , @Param("loginPassword") String loginPassword);

    /**
     * 根据名称和提交密码获取用户信息
     * @param name
     * @return
     */
    public UserBean getUserByNameAndSubmitPassword(@Param("name") String name , @Param("submitPassword") String submitPassword);

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public UserBean getUser(int id);

    /**
     * 新增一条用户信息记录
     * @param u
     */
    public int insertUser(UserBean u);

    /**
     * 删除一条用户信息记录
     * @param id
     */
    public int deleteUser(int id);

    /**
     * 根据用户id删除 用户、角色关联表记录
     * @param id
     * @return
     */
    public int deleteUserRole(int id);

    /**
     * 根据用户id删除 工区、用户、角色关联的表记录
     * @param id
     * @return
     */
    public int deleteWorkSpaceUserRole(int id);

    /**
     * 更新一条用户信息记录
     * @param u
     */
    public int updateUser(UserBean u);

    /**
     * 获取所有的用户信息
     * @return
     */
    public UserBean[] getAllUsers();
}
