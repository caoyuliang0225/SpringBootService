package com.yl.dto;

import com.yl.entity.JWT;
import com.yl.entity.User;
import lombok.Data;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Data
public class UserLoginDTO {

    private JWT jwt;

    private User user;
}
