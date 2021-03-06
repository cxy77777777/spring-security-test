package com.sangeng.sangengspringsecurity.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sangeng.sangengspringsecurity.dto.LoginUser;
import com.sangeng.sangengspringsecurity.entity.SysUserEntity;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq( "username", username);
        //查询用户信息
        SysUserEntity userEntity = userMapper.selectOne(wrapper);
        //如果没有查询到用户就抛出异常----spring-security-存在异常捕获过滤器
        if(Objects.isNull(userEntity)){
            throw new RuntimeException("用户名或密码错误!");
        }
        //查询对应的权限信息
        List<String> listPermis = sysMenuMapper.getPermsByUserId(userEntity.getId());
        //去掉list中的null
        List<String> newList = listPermis.stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        //去掉list空字符串
        List<String> filtered=newList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        //把数据封装成UserDetails返回
        return new LoginUser(userEntity,filtered);
    }
}
