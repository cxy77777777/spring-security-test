package com.sangeng.sangengspringsecurity.controller;

import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.dto.LoginUser;
import com.sangeng.sangengspringsecurity.dto.SysUserDTO;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("user/login")
    public Result login(@RequestBody SysUserDTO sysUserDTO){
        return loginService.login(sysUserDTO);
    }

    @GetMapping("user/loginOut")
    public Result loginOut(){
        loginService.loginOut();
        return new Result();
    }

    @GetMapping("permission/generateRoutes")
    public Result generateRoutes(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result().ok(loginService.generateRoutes(loginUser.getUserEntity().getId()));
    }
}
