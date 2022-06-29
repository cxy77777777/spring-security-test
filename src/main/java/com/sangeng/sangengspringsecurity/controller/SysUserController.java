package com.sangeng.sangengspringsecurity.controller;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.service.SysUserService;
import com.sangeng.sangengspringsecurity.utils.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户token获取权限菜单及用户相关信息
     * @param token
     * @return
     */
    @GetMapping("getInfo")
    public Result getInfo(String token){
        return new Result().ok(sysUserService.getInfo(SpringSecurityUtils.getUserIdByToken(token)));
    }
}
