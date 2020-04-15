package com.yl.web;

import com.yl.dto.UserLoginDTO;
import com.yl.entity.User;
import com.yl.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@RestController
public class UserController {

    @Autowired
    UserServiceDetail userServiceDetail;

    @PostMapping("/user/register")
    public User postUser(@RequestParam("username") String username ,@RequestParam("password") String password){
        //参数判断，省略
       return userServiceDetail.insertUser(username,password);
    }

    @PostMapping("/user/login")
    public UserLoginDTO login(@RequestParam("username") String username , @RequestParam("password") String password){
        //参数判断，省略
        return userServiceDetail.login(username,password);
    }
}
