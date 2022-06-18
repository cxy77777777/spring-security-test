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
 * Spring Security默认是禁用注解的，要想开启注解， 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
 * 来判断用户对某个控制层的方法是否具有访问权限 
 *
 * 当我们想要开启spring方法级安全时，只需要在任何 @Configuration实例上使用 @EnableGlobalMethodSecurity 注解就能达到此目的。
 * 同时这个注解为我们提供了prePostEnabled 、securedEnabled 和 jsr250Enabled 三种不同的机制来实现同一种功能：
 * prePostEnabled ：
 * prePostEnabled = true 会解锁 @PreAuthorize 和 @PostAuthorize 两个注解。从名字就可以看出@PreAuthorize 注解会在方法执行前进行验证，而 @PostAuthorize 注解会在方法执行后进行验证。
 * @PostAuthorize： 该注解使用不多，在方法执行后再进行权限验证。 适合验证带有返回值的权限。Spring EL 提供 返回对象能够在表达式语言中获取返回的对象returnObject。区别在于先执行方法。而后进行表达式判断。如果方法没有返回值实际上等于开放权限控制；如果有返回值实际的结果是用户操作成功但是得不到响应。允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常。
 *
 * @PreFilter： 对集合类型的参数执行过滤，移除结果为false的元素。基于方法入参相关的表达式，对入参进行过滤。分页慎用！该过程发生在接口接收参数之前。 入参必须为 java.util.Collection 且支持 remove(Object) 的参数。如果有多个集合需要通过 filterTarget=<参数名> 来指定过滤的集合。内置保留名称 filterObject 作为集合元素的操作名来进行评估过滤。
 * @PostFilter： 和@PreFilter 不同的是， 基于返回值相关的表达式，对返回值进行过滤。分页慎用！该过程发生接口进行数据返回之前。
 *
 * Secured：
 * @Secured注解是用来定义业务方法的安全配置。在需要安全[角色/权限等]的方法上指定 @Secured，并且只有那些角色/权限的用户才可以调用该方法。
 * @Secured缺点（限制）就是不支持Spring EL表达式。不够灵活。并且指定的角色必须以ROLE_开头，不可省略。该注解功能要简单的多，默认情况下只能基于角色（默认需要带前缀 ROLE_）集合来进行访问控制决策。该注解的机制是只要其声明的角色集合（value）中包含当前用户持有的任一角色就可以访问。也就是 用户的角色集合和 @Secured 注解的角色集合要存在非空的交集。 不支持使用 SpEL 表达式进行决策。
 *
 * jsr250E：
 * 启用 JSR-250 安全控制注解，这属于 JavaEE 的安全规范（现为 jakarta 项目）。一共有五个安全注解。如果你在 @EnableGlobalMethodSecurity 设置 jsr250Enabled 为 true ，就开启了 JavaEE 安全注解中的以下三个：
 *
 * 1.@DenyAll： 拒绝所有访问
 *
 * 2.@RolesAllowed({“USER”, “ADMIN”})： 该方法只要具有"USER", "ADMIN"任意一种权限就可以访问。这里可以省略前缀ROLE_，实际的权限可能是ROLE_ADMIN
 *
 * 3.@PermitAll： 允许所有访问
 *
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
