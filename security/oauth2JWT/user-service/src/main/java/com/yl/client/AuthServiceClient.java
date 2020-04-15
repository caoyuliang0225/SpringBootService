package com.yl.client;


import com.yl.client.hystrix.AuthServiceHystrix;
import com.yl.entity.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */

@FeignClient(value = "jwt-uaa-service",fallback = AuthServiceHystrix.class )
public interface AuthServiceClient {

    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization,
                 @RequestParam("grant_type") String type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);

}



