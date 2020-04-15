package com.yl.entity;

import lombok.Data;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Data
public class JWT {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private int expires_in;

    private String scope;

    private String jti;

    @Override
    public String toString() {
        return "JWT{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                ", jti='" + jti + '\'' +
                '}';
    }
}
