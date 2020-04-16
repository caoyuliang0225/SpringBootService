package com.yl.crm.controller;

import com.yl.crm.data.model.Role;
import com.yl.crm.data.model.User;
import com.yl.crm.service.RoleService;
import com.yl.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cao Yuliang on 2019-08-22.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleList.add(this.roleService.getById(1L));
        user.setAuthorities(roleList);
        User u = this.userService.save(user);
        return u;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public void delUser(@RequestParam Long id) {
        this.userService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(@RequestParam Long id) {
        return this.userService.getById(id);
    }
}
