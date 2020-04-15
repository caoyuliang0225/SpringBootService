package com.yl.config;

import com.yl.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private UserServiceDetail userServiceDetail;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray());
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        JwtAccessTokenConverter converter = new JwtAccessToken();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fzp-jwt"));
        return converter;
    }

    /**
     * spring 升级5.0后，secret需要加密，否则 id is null/not look like BCrypt
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("jwt-user-service")
                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"))
                .scopes("server")
                .autoApprove(true)
                .authorizedGrantTypes("implicit","refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(12*300);//5min过期
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .reuseRefreshTokens(false).userDetailsService(userServiceDetail)
                .authenticationManager(authenticationManager);
    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
//    }
}
