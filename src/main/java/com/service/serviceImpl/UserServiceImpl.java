package com.service.serviceImpl;

import cn.hutool.core.util.IdUtil;
import com.dao.UserMapper;
import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 用户注册
    @Override
    public void register(User user) {
        String salt = IdUtil.simpleUUID().toUpperCase();
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,2);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        user.setCreatetime(new Date());
        userMapper.save(user);
    }

    // 根据用户名查询
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User findByRolesUserName(String username) {
        return userMapper.findByRolesUserName(username);
    }

    @Override
    public Role findByPermissionRoleId(Integer id) {
        return userMapper.findByPermissionRoleId(id);
    }
}