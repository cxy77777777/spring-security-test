package com.sangeng.sangengspringsecurity.service.Impl;

import com.sangeng.sangengspringsecurity.dto.LoginUser;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.mapper.UserMapper;
import com.sangeng.sangengspringsecurity.service.SysUserService;
import com.sangeng.sangengspringsecurity.utils.CollectionTools;
import com.sangeng.sangengspringsecurity.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据用户id获取权限菜单及用户相关信息
     * @return
     */
    @Override
    public Map<String,Object> getInfo(Long id) {
        LoginUser loginUser = redisCache.getCacheObject("login:" + id);

        //查询对应的权限信息
        List<String> listPermis = sysMenuMapper.getPermsByUserId(id);
        if (!CollectionUtils.isEmpty(listPermis)){
            listPermis = CollectionTools.removeEmptyString(listPermis);
        }
        Map<String,Object> map = new HashMap<>();
        //添加权限菜单
        map.put("roles",listPermis);
        //添加名称
        map.put("name",loginUser.getUserEntity().getRealName());
        //添加头像
        map.put("avatar","https://shujutong.oss-cn-north-2.unicloudsrv.com/zhaijidi/877515c2c1bd41d4bd07064dbcfd2659/1.jpg");
        return map;
    }
}
