package com.sangeng.sangengspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

    /**
     * 四种鉴权函数：一般就用hasAuthority就可以。
     * hasAuthority：具有test权限才可以访问，该注解编译时会把内部字符串当作函数执行，hasAuthority('test')，返回布尔类型，true允许访问，false不允许
     * hasAnyAuthority：具有这两个中任意一个权限就可以访问，该注解编译时会把内部字符串当作函数执行，hasAnyAuthority('test','admin')，返回布尔类型，true允许访问，false不允许
     * hasRole：要有对应的角色才能访问，但是它内部会把我们传入的参数拼接上ROLE_后再去比较，所以这种情况下要用用户对应的权限也要有ROLE_这个前缀才可以。
     * hasAnyRole：对比hasRole，它可以传入多个权限参数，符合任意一个就可以。
     *@ex.hasAuthority（）：在SPEL表达式中使用@ex相当于获取容器中的bean的名字ex的对象，然后再调用这个对象的hasAuthority（）方法
     *
     * @return
     */
    @GetMapping("getHello")
//    @PreAuthorize("hasAuthority('homestead:homesteadinfoproblem:update')")//具有test权限才可以访问，该注解编译时会把内部字符串当作函数执行，hasAuthority('test')，返回布尔类型，true允许访问，false不允许
//    @PreAuthorize("hasAnyAuthority('admin','homestead:homesteadinfoproblem:update')")//具有这两个中任意一个权限就可以访问，该注解编译时会把内部字符串当作函数执行，hasAnyAuthority('test','admin')，返回布尔类型，true允许访问，false不允许
//    @PreAuthorize("hasRole('ROLE_homestead:homesteadinfoproblem:update')")
//    @PreAuthorize("hasAnyRole('admin','ROLE_homestead:homesteadinfoproblem:update')")
    @PreAuthorize("@ex.hasAuthority('homestead:homesteadinfoproblem:update')")//使用自定义鉴权函数
    public String getHello(){
        return "hello";
    }

    @GetMapping("testCros")
    public String testCros(){
        return "testCros";
    }
}
