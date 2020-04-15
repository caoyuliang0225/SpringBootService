package com.yl.config;

import com.yl.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by Cao Yuliang on 2020-04-15.
 */
public class JwtAccessToken extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        String phone = ((User) authentication.getPrincipal()).getPhone();
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        defaultOAuth2AccessToken.getAdditionalInformation().put("phone", phone);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }
}
