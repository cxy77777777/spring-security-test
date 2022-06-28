package com.sangeng.sangengspringsecurity.service;

import com.sangeng.sangengspringsecurity.dto.SysUserDTO;

import java.util.Map;

public interface SysUserService {

    /**
     * 用户详情
     * @return
     */
    Map<String,Object> getInfo(Long id);

}
