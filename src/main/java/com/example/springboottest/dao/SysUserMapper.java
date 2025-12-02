package com.example.springboottest.dao;

import com.example.springboottest.entity.SysUser;
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
    SysUser findByName(String username);
}

