package com.sangeng.sangengspringsecurity.expression;

import com.sangeng.sangengspringsecurity.dto.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component("ex")
public class SGExpressionRoot {

    public final boolean hasAuthority(String authority) {
        //1.获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> list = loginUser.getPermissions();
        Set hasSet = list.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        //2.判断用户权限集合中是否存在authority
        return hasSet.contains(authority);
    }
}
