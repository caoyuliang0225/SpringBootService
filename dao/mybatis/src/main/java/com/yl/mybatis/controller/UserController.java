package com.yl.mybatis.controller;

import com.yl.mybatis.dao.UserMapper;
import com.yl.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getById(@RequestParam Long id) {
        User user = this.userMapper.findById(id);
        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        this.userMapper.save(user);
        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        this.userMapper.update(user);
        return user;
    }
}
