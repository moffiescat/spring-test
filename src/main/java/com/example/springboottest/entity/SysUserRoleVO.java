package com.example.springboottest.entity;

import lombok.Data;

// 新增VO类封装用户+角色信息
@Data
public class SysUserRoleVO {
    private Long id;
    private String username;
    private String password;
    private String roleName;
}
