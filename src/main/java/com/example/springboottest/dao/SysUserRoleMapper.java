package com.example.springboottest.dao;

import com.example.springboottest.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SysUserRoleMapper {
    /**
     * 根据用户ID查找用户角色
     *
     * @param userId 用户ID
     * @return 用户角色
     */
    @Select("select * from sys_user_role where user_id = #{userId}")
    List<SysUserRole> findByUserId(Long userId);
}
