package com.sangeng.sangengspringsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.sangeng.sangengspringsecurity.mapper")
public class SangengSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SangengSpringSecurityApplication.class, args);
    }

}
