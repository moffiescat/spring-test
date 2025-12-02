package com.example.springboottest.service;

import com.example.springboottest.dao.SysRoleMapper;
import com.example.springboottest.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectById(Long id){
        return roleMapper.findById(id);
    }
}

