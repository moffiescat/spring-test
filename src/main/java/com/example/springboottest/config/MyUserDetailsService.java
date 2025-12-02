package com.example.springboottest.config;

import com.example.springboottest.entity.SysRole;
import com.example.springboottest.entity.SysUser;
import com.example.springboottest.entity.SysUserRole;
import com.example.springboottest.service.SysRoleService;
import com.example.springboottest.service.SysUserRoleService;
import com.example.springboottest.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //从数据库中取出信息
        SysUser user = userService.selectByName(username);

        if(user==null){throw new UsernameNotFoundException("用户名不存在");}

        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        for(SysUserRole userRole : userRoles){
            SysRole role = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(user.getUsername(),user.getPassword(),authorities);
    }

}
