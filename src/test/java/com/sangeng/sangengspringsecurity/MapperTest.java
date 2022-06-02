package com.sangeng.sangengspringsecurity;

import com.sangeng.sangengspringsecurity.entity.SysUserEntity;
import com.sangeng.sangengspringsecurity.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * 2.5.0 springboot 使用测试类，只需要添加注解@SpringBootTest
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<SysUserEntity> list = userMapper.selectList(null);
        System.out.println(list);
    }

    /**
     * BCryptPasswordEncoder加密测试
     */
    @Test
    public void BCryptPasswordEncoderTest(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s1 = encoder.encode("1234");
        String s2 = encoder.encode("1234");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(encoder.matches("1234","$2a$10$Ik20umWYSbfwmkp46Otzyu7P.kZz0lPNBGhScvI9yfZw2MM.RNoD."));
    }
}
