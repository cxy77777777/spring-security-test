package com.sangeng.sangengspringsecurity.config;

import com.sangeng.sangengspringsecurity.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 定义spring-Securit配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;


    //创建BCryptPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager认证注入spring容器
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //过滤拦截配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                //关闭csrf，前后端分离项目因为已配置jwt-token认证，即已经屏蔽掉了CSRF攻击，所以不在需要开启csrf，
                // 如果不关闭则会进行csrf_token的校验，一般情况下请求头中不带csrf_token，若不关闭则会校验不通过。
                csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口允许匿名访问
                .antMatchers("/user/login").anonymous()
                //基于配置的权限控制
                .antMatchers("/testCros").hasAuthority("homestead:homesteadinfoproblem:update")
                //出上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //把定义的jwtAuthenticationTokenFilter过滤器位置放在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)//配置认证失败处理器
                .accessDeniedHandler(accessDeniedHandler);         //配置授权失败处理器

        //配置SpringSecurity允许跨域
        http.cors();
    }
}
