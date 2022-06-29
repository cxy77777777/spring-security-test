package com.sangeng.sangengspringsecurity.service;

import java.util.Map;

public interface SysUserService {

    /**
     * 根据用户id获取权限菜单及用户相关信息
     * @return
     */
    Map<String,Object> getInfo(Long id);

}
