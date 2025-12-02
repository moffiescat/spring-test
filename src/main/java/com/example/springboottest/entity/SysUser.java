package com.example.springboottest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    /** 主键 */
    private Long id;

    /** 用户名. */
    private String username;

    /** 密码. */
    private String password;
}