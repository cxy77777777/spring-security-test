package com.sangeng.sangengspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("getHello")
    @PreAuthorize("hasAuthority('test')")
    public String getHello(){
        return "hello";
    }
}
