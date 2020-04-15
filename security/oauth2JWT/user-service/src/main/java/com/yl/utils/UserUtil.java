package com.yl.utils;

import lombok.Data;

import java.util.List;

/**
 * Created by Cao Yuliang on 2020-04-15.
 */
@Data
public class UserUtil {

    private Long exp;

    private String user_name;

    private List<String> authorities;

    private String jti;

    private String client_id;

    private List<String> scope;

    private String phone;
}
