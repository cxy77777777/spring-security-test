package com.sangeng.sangengspringsecurity.controller;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;
import com.sangeng.sangengspringsecurity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("user/login")
    public Result login(@RequestBody SysUserDTO sysUserDTO){
        return loginService.login(sysUserDTO);
    }
}
