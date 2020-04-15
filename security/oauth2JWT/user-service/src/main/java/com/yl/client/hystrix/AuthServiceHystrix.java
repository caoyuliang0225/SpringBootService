package com.yl.client.hystrix;


import com.yl.client.AuthServiceClient;
import com.yl.entity.JWT;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {

    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
