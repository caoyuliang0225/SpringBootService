# Spring Security

##Basic认证基础
1，浏览器做登录操作，后台需要将 Basic认证打开，打开后在  header里将用户名密码 base64编码后传到后台，之后调用接口才是正常的，否则都是403，当前端页面发现401后悔自动跳转到登录页面，要求用户登录。

2，浏览器的basic认证，在header里添加<br>
Authorization     :     Basic YWRtaW46cGFzcw== <br>
Content-Type      :     application/json <br>

###XSRF/CORF 跨站请求伪造
同一浏览器，登录某网址后，浏览器会自动带上cookie访问api，<br>
另开一个tab页，其他网站发送一个api，只要域名和前网站一致就可以携带cookie，<br>
进行对前网站的访问
HTTP头 X-XSRFToken 通过添加一个token解决

Spring Security服务 主要类是 com.yl.crm.config.SecurityConfig 
1，更新版本后，密码必须加密，否则报错 <br>
2，.permitAll要写在前面，.anyRequest().authenticated()写在后面，否则permitAll不生效 <br>
3，.antMatchers("/user/delete").hasRole("ADMIN")这种写法，<br>
和在Controller方法上加注释效果相同@PreAuthorize("hasAuthority('ROLE_ADMIN')") <br>
4，登录接口 *POST* http://localhost:10000/login?username=user&password=123456 <br>
5，退出登录 *GET* http://localhost:10000/logout <br>
6，public void configureGlobal(AuthenticationManagerBuilder auth) {} <br>
内存用户和数据库用户只能一个启用，谁在前面谁生效 <br>
7，不显示写 http.httpBasic() 就不会弹出默认的登录框 <br>
8，登录退出实现相应的 handler 并注入后，即可实现前后端分离开发 <br>
9，权限：用户表实体类实现 UserDetails，authorities使用 权限实体，<br>
权限实体 实现 GrantedAuthority，自定义 UserDetailsService 的实现类，<br>
通过数据库查出符合String参数的用户，<br>
并将 UserDetailsService 赋给 auth.userDetailsService(userDetailsService);


oauth2
授权节点：/oauth/authorize
获取 Token 节点：/oauth/token

spring-cloud-starter-oauth2 包括 spring-cloud-starter-security, spring-security-oauth2,spring-security-jwt

