package com.sangeng.sangengspringsecurity.utils;

import com.sangeng.sangengspringsecurity.dto.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity相关方法
 */
@Component
public class SpringSecurityUtils {

    /**
     * 获取当前登录用户
     * @return
     */
    public static LoginUser getUserInfo(){
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     *根据token获取对应的用户id
     */
    public static Long getUserIdByToken(String token){
        try {
            Claims claims = JwtUtil.parseJWT(token);
            Long userId  = Long.parseLong(claims.getSubject());
            return userId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
