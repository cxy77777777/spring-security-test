package com.sangeng.sangengspringsecurity.service.Impl;

import com.sangeng.sangengspringsecurity.dto.LoginUser;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.mapper.UserMapper;
import com.sangeng.sangengspringsecurity.service.SysUserService;
import com.sangeng.sangengspringsecurity.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据用户id获取详情
     * @return
     */
    @Override
    public Map<String,Object> getInfo(Long id) {
        LoginUser loginUser = redisCache.getCacheObject("login:" + id);
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(loginUser.getUserEntity(),sysUserDTO);

        //查询对应的权限信息
        List<String> listPermis = sysMenuMapper.getPermsByUserId(id);
        //去掉list中的null
        List<String> newList = listPermis.stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        //去掉list空字符串
        List<String> filtered=newList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("roles",filtered);
        map.put("name","admin");
        map.put("avatar",filtered);
        return map;
    }
}
