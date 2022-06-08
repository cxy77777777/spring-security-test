package com.sangeng.sangengspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("getHello")
    @PreAuthorize("hasAuthority('test')")//具有test权限才可以访问，该注解编译时会把内部字符串当作函数执行，hasAuthority('test')，返回布尔类型，true允许访问，false不允许
    public String getHello(){
        return "hello";
    }
}
