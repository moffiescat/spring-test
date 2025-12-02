package com.example.springboottest.service;

import com.example.springboottest.dao.SysUserMapper;
import com.example.springboottest.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser selectByName(String username) {


        return sysUserMapper.findByName(username);
    }
}
