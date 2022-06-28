package com.sangeng.sangengspringsecurity.service;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;

import java.util.List;

public interface LoginService {

    /**
     * 登录
     * @param sysUserDTO
     * @return
     */
    public Result login(SysUserDTO sysUserDTO);

    /**
     * 退出
     */
    void loginOut();

    /**
     *根据userid获取权限
     * @param userId
     * @return
     */
    List<String> generateRoutes(Long userId);
}
