package com.example.springboottest.service;

import com.example.springboottest.dao.SysUserRoleMapper;
import com.example.springboottest.entity.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(Long userId) {
        return userRoleMapper.findByUserId(userId);
    }
}