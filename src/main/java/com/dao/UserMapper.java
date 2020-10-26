package com.dao;

import com.entity.Role;
import com.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 用户注册
    void save(User user);

    // 根据用户名查询
    User findByUserName(@Param("username") String username);

    // 根据用户名查询角色
    User findByRolesUserName(@Param("username") String username);

    // 根据角色id查询角色
    Role findByPermissionRoleId(@Param("id") Integer id);
}
