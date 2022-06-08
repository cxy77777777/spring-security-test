package com.sangeng.sangengspringsecurity.service;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;

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
}
