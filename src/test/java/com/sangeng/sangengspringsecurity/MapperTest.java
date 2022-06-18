package com.sangeng.sangengspringsecurity;

import com.sangeng.sangengspringsecurity.entity.SysUserEntity;
import com.sangeng.sangengspringsecurity.mapper.SysMenuMapper;
import com.sangeng.sangengspringsecurity.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 2.5.0 springboot 使用测试类，只需要添加注解@SpringBootTest
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Test
    public void testUserMapper(){
        List<SysUserEntity> list = userMapper.selectList(null);
        System.out.println(list);
    }

    @Test
    public void testSysMenuMapper(){
        List<String> list = sysMenuMapper.getPermsByUserId(1374323437342806017l);
        //去掉list中的null
        List<String> newList = list.stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        //去掉list空字符串
        List<String> filtered=newList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(System.out::println);
        System.out.println(filtered);
    }

    /**
     * BCryptPasswordEncoder加密测试
     */
    @Test
    public void BCryptPasswordEncoderTest(){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s1 = passwordEncoder.encode("1234");
        String s2 = passwordEncoder.encode("1234");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(passwordEncoder.matches("1234","$2a$10$Ik20umWYSbfwmkp46Otzyu7P.kZz0lPNBGhScvI9yfZw2MM.RNoD."));
    }
}
