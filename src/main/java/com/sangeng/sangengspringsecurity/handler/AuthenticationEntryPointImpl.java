package com.sangeng.sangengspringsecurity.handler;

import com.alibaba.fastjson.JSON;
import com.sangeng.sangengspringsecurity.domain.Result;
import com.sangeng.sangengspringsecurity.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败返回值设置
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result().error(HttpStatus.UNAUTHORIZED.value(),"用户认证失败！");
        String str = JSON.toJSONString(result);
        WebUtils.renderString(response,str);
    }
}
