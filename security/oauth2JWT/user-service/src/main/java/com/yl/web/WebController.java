package com.yl.web;

import com.yl.utils.JacksonUtil;
import com.yl.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Slf4j
@RestController
public class WebController {

    @RequestMapping(value = "/foo", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getFoo() {
        return "i'm foo, " + UUID.randomUUID().toString();
    }

    @RequestMapping(value = "/foo/decode", method = RequestMethod.GET)
    public String getFoo2(Authentication authentication) {

        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = oAuth2AuthenticationDetails.getTokenValue();
        Jwt jwt = JwtHelper.decode(token);
//      claims  {"exp":1586916321,"user_name":"wahaha","authorities":["ROLE_USER","ROLE_ADMIN"],"jti":"67f3c487-b421-471d-993d-22341d405e6a","client_id":"jwt-user-service","scope":["server"]}
        String claims = jwt.getClaims();
        try {
            UserUtil userUtil = JacksonUtil.readValue(claims, UserUtil.class);
            logger.info("userUtil: {}", userUtil.getUser_name());
            logger.info("userUtil: {}", userUtil.getAuthorities());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "i'm foo, " + UUID.randomUUID().toString();
    }

    @RequestMapping(value = "/foo/getPrincipal", method = RequestMethod.GET)
    public String getPrincipal(OAuth2Authentication oauth2Authentication, Principal principal, Authentication authentication) {

        logger.info("oauth2Authentication: {}", oauth2Authentication);
        oauth2Authentication.getPrincipal();
        oauth2Authentication.getAuthorities();
        logger.info("principal: {}", principal);
        principal.getName();
        logger.info("authentication: {}", authentication);
        authentication.getPrincipal(); // Principal: wahaha
        authentication.getAuthorities(); // Authorities: ROLE_USER, ROLE_ADMIN

        return "i'm foo, " + UUID.randomUUID().toString();
    }
}
