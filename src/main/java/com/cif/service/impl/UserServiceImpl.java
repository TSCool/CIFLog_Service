package com.cif.service.impl;

import com.cif.dao.UserMapper;
import com.cif.domain.UserBean;
import com.cif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据名称获取用户信息
     * @param name
     * @return
     */
    @Override
    public UserBean getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    /**
     * 根据名称和密码获取用户信息
     * @param name
     * @param loginPassword
     * @return
     */
    @Override
    public UserBean getUserByNameAndPassword(String name, String loginPassword) {
        return userMapper.getUserByNameAndPassword(name,loginPassword);
    }

    /**
     * 根据名称和提交密码获取用户信息
     * @param name
     * @return
     */
    @Override
    public UserBean getUserByNameAndSubmitPassword(String name, String submitPassword) {
        return userMapper.getUserByNameAndSubmitPassword(name,submitPassword);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @Override
    public UserBean getUser(int id) {
        return userMapper.getUser(id);
    }

    /**
     * 新增一条用户信息记录
     * @param u
     */
    @Override
    public int insertUser(UserBean u) {
        return userMapper.insertUser(u);
    }

    /**
     * 删除一条用户信息记录
     * @param id
     */
    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    /**
     * 根据用户id删除 用户、角色关联表记录
     * @param id
     * @return
     */
    @Override
    public int deleteUserRole(int id) {
        return userMapper.deleteUserRole(id);
    }

    /**
     * 根据用户id删除 工区、用户、角色关联的表记录
     * @param id
     * @return
     */
    @Override
    public int deleteWorkSpaceUserRole(int id) {
        return userMapper.deleteWorkSpaceUserRole(id);
    }

    /**
     * 更新一条用户信息记录
     * @param u
     */
    @Override
    public int updateUser(UserBean u) {
        return userMapper.updateUser(u);
    }

    /**
     * 获取所有的用户信息
     * @return
     */
    @Override
    public UserBean[] getAllUsers() {
        return userMapper.getAllUsers();
    }
}
