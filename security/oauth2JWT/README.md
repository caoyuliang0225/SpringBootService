# SpringBootService
## security
### oauth2+JWT

开发者的本地环境为 mac

sql目录下，脚本先执行，在数据库中创建相应的表，或者将SQL脚本写在服务里，启动服务自动执行

>		<dependency>
>			<groupId>org.springframework.cloud</groupId>
>			<artifactId>spring-cloud-starter-oauth2</artifactId>
>		</dependency>
		
这个包中包含了

>		<dependency>
>			<groupId>org.springframework.security</groupId>
>			<artifactId>spring-security-jwt</artifactId>
>		</dependency>
>		<dependency>
>			<groupId>org.springframework.security.oauth</groupId>
>			<artifactId>spring-security-oauth2</artifactId>
>		</dependency>
	
####1，AccessToken的获取	（uaa）
使用密码获取 **AccessToken**

（clientId:password jwt-user-service:123456 的 Base64 编码）

*HEADER* Authorization:Basic and0LXVzZXItc2VydmljZToxMjM0NTY= 

*POST* http://localhost:8099/oauth/token?grant_type=password&username=XXX&password=XXX

返回：
{
  "access_token": "XXX"
  "token_type": "bearer",
  "refresh_token": "XXX"
  "expires_in": 3599,
  "scope": "server",
  "phone": "13812345678",
  "jti": "aabdfd04-f5e0-456a-8c5b-c0c6150a490e"
}


使用 RefreshToken 获取 AccessToken

（clientId:password jwt-user-service:123456 的 Base64 编码）

HEADER Authorization:Basic and0LXVzZXItc2VydmljZToxMjM0NTY= 

POST http://localhost:8099/oauth/token?grant_type=refresh_token&refresh_token=XXX

返回：
{
  "access_token": "XXX"
  "token_type": "bearer",
  "refresh_token": "XXX"
  "expires_in": 3599,
  "scope": "server",
  "phone": "13812345678",
  "jti": "aabdfd04-f5e0-456a-8c5b-c0c6150a490e"
}

####2，JWT的扩展（uaa）
com.yl.config.JwtAccessToken.java

####3，注册登录 （user）
调用登录接口会远程获取JWT
