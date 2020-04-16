package com.yl.crm.config;

import com.yl.crm.config.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Cao Yuliang on 2019-08-22.
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别的安全校验
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //权限拒绝处理逻辑
    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //会话失效(账号被挤下线)处理逻辑
    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //访问决策管理器
//    @Autowired
//    CustomizeAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

//    @Autowired
//    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    @Autowired
    private PermitAuthenticationFilter permitAuthenticationFilter;


    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 新版本如果密码不加密将报错
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 内存用户和数据库用户只能一个启用，谁在前面谁生效
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        String username ="user";
//        String password ="123456";
//        //暂时使用基于内存的AuthenticationProvider
//        auth.inMemoryAuthentication().
//                withUser(username).password(this.passwordEncoder().encode(password)).roles("ADMIN", "USER");

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.addFilterBefore(permitAuthenticationFilter, FilterSecurityInterceptor.class);

        http
                .authorizeRequests()

                .antMatchers("/**/*.css").permitAll()
                .antMatchers("/**/*.js").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/hello").permitAll()
                .antMatchers(HttpMethod.GET, "/**/token").permitAll()
                .antMatchers(HttpMethod.GET, "/**/principal").permitAll()
                .antMatchers("/user/login", "/user/register").permitAll()

                .antMatchers("/user/delete").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()

                .and()
                    .formLogin()
                    .permitAll()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID");


        // H2控制台需要
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 为浏览器弹出登录的对话框
//        http.httpBasic().disable();
        // 可以使用下面的方法代替上面两行


        /**
         * 经测试，.antMatchers("/user/delete").hasRole("ADMIN")这种写法
         * 和在Controller方法上加注释效果相同
         * @PreAuthorize("hasAuthority('ROLE_ADMIN')")
         * .permitAll()的 URL 需要写在前面
         */

//        http.cors().and().csrf().disable();

//        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);


//        http.authorizeRequests()
//                .antMatchers("/user/register").permitAll()
//                .anyRequest().authenticated();
//                .
                //antMatchers("/getUser").hasAuthority("query_user").
                //antMatchers("/**").fullyAuthenticated().
//                        withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
//                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
//                        return o;
//                    }
//                }).
                //登出
//                        and().logout().
//                permitAll().//允许所有用户
//                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
//                deleteCookies("JSESSIONID").//登出之后删除cookie
//                //登入
//                        and().formLogin().
//                permitAll().//允许所有用户
//                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
//                failureHandler(authenticationFailureHandler).//登录失败处理逻辑
//                //异常处理(权限拒绝、登录失效等)
////                        and().exceptionHandling().
////                accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
////                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
//                //会话管理
//                        and().sessionManagement().
//                maximumSessions(1).//同一账号同时登录最大用户数
//                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑


//        http.authorizeRequests()
//                .antMatchers("/user/login", "/user/register").permitAll();

        /**
         * POST http://localhost:10000/login?username=user&password=123456
         * GET http://localhost:10000/logout
         */
//        http.csrf().disable();
//        http.headers().frameOptions().disable();

//        http.cors().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")

                // allow anonymous resource requests
                .antMatchers(
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/h2-console/**"
                );
    }
}
