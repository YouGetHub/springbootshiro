package com.service;

import com.entity.Role;
import com.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    // 用户注册
    void register(User user);

    // 根据用户名查询
    User findByUserName(@Param("username") String username);

    // 根据用户查询角色
    User findByRolesUserName(@Param("username") String username);

    // 根据角色id查询角色
    Role findByPermissionRoleId(@Param("id") Integer id);
}
