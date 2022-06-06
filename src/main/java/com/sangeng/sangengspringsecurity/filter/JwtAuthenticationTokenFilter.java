package com.sangeng.sangengspringsecurity.filter;

import com.sangeng.sangengspringsecurity.entity.SysUserEntity;
import com.sangeng.sangengspringsecurity.utils.JwtUtil;
import com.sangeng.sangengspringsecurity.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * OncePerRequestFilter使用继承OncePerRequestFilter过滤器 可以避免进行多次重复过滤
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //2.解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId  = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法！");
        }
        //3.从redis中获取用户信息
        SysUserEntity sysUserEntity = redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(sysUserEntity)){
            throw new RuntimeException("用户未登录！");
        }
        //4.存入SecurityContextHolder,用于后面的过滤器获取信息
        //UsernamePasswordAuthenticationToken（）需要用三个参数构造，三个参数 会把该标志位置为true，代表认证通过
        //需要获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sysUserEntity,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //5.认证通过放行filterChain.doFilter(request,response);
        filterChain.doFilter(request,response);
    }
}
