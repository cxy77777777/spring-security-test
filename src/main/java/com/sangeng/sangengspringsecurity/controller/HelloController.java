package com.sangeng.sangengspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("getHello")
    public String getHello(){
        return "hello";
    }
}
