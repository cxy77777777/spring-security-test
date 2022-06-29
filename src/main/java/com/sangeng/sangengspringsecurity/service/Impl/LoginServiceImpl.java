package com.sangeng.sangengspringsecurity.service.Impl;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.dto.LoginUser;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;
import com.sangeng.sangengspringsecurity.entity.SysUserEntity;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.service.LoginService;
import com.sangeng.sangengspringsecurity.utils.JwtUtil;
import com.sangeng.sangengspringsecurity.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 登录
     * @param sysUserDTO
     * @return
     */
    @Override
    public Result login(SysUserDTO sysUserDTO) {
        //1.AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUserDTO.getUsername(),sysUserDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //2.如果认证没通过给出对应的提示
        if (Objects.isNull(authentication)){
            throw new RuntimeException("登陆失败!");
        }
        //3.如果认证通过了，使用userid生成jwt，jwt存入Result返回
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        SysUserEntity userEntity = loginUser.getUserEntity();
        String jwt = JwtUtil.createJWT(userEntity.getId().toString(), 60*60*60*1000L);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //4.把完整的用户信息存入redis，userid作为key
        redisCache.setCacheObject("login:" + userEntity.getId().toString(),loginUser,60*60, TimeUnit.SECONDS);
        return new Result().ok(map);
    }

    /**
     * 退出
     */
    @Override
    public void loginOut() {
        //1.从SecurityContextHolder中获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //2.根据登录用户的userid删除redis中的用户信息
        redisCache.deleteObject("login:" + loginUser.getUserEntity().getId());
    }

    /**
     *根据userid获取权限
     * @param userId
     * @return
     */
    @Override
    public List<String> generateRoutes(Long userId) {
        return sysMenuMapper.getPermsByUserId(userId);
    }
}
