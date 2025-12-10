package com.example.springboottest.dao;

import com.example.springboottest.entity.SysUser;
import com.example.springboottest.entity.SysUserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("select * from sys_user where username = #{username}")

    // SysUserMapper新增方法
    @Select("SELECT u.*, r.name AS role_name " +
            "FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN sys_role r ON ur.role_id = r.id " +
            "WHERE u.username = #{username}")
    List<SysUserRoleVO> findUserWithRoles(String username);

    SysUser findByName(String username);
}

